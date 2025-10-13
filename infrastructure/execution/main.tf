terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.region
}

resource "aws_security_group" "lb_sg" {
  name        = "citizen-lb-sg"
  description = "Security group for Load Balancer"

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "app_sg" {
  name        = "citizen-app-sg"
  description = "Security group for app instances"

  ingress {
    from_port       = 8080
    to_port         = 8080
    protocol        = "tcp"
    security_groups = [aws_security_group.lb_sg.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "db_sg" {
  name        = "citizen-db-sg"
  description = "Security group for DB instance"

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [aws_security_group.app_sg.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "citizen_db" {
  ami           = var.base_ami
  instance_type = "t3.micro"
  key_name      = var.key_name
  security_groups = [aws_security_group.db_sg.name]

  user_data = <<-EOF
              #!/bin/bash
              sudo yum install -y mysql-server
              sudo systemctl enable mysqld
              sudo systemctl start mysqld

              # Create database and user
              mysql -u root -e "CREATE DATABASE citizen_registry;"
              mysql -u root -e "CREATE USER 'citizen'@'%' IDENTIFIED BY 'citizen';"
              mysql -u root -e "GRANT ALL PRIVILEGES ON citizen_registry.* TO 'citizen'@'%';"
              mysql -u root -e "FLUSH PRIVILEGES;"

              # Allow remote connections
              sudo sed -i "s/^bind-address.*/bind-address = 0.0.0.0/" /etc/my.cnf
              sudo systemctl restart mysqld
              EOF

  tags = {
    Name = "citizen-db"
  }
}

resource "aws_instance" "citizen_app" {
  count         = 3
  ami           = var.citizen_ami_id
  instance_type = "t3.micro"
  key_name      = var.key_name
  security_groups = [aws_security_group.app_sg.name]

  user_data = <<-EOF
              #!/bin/bash
              export SPRING_DATASOURCE_URL=jdbc:mysql://${aws_instance.citizen_db.private_ip}:3306/citizen_registry
              export SPRING_DATASOURCE_USERNAME=citizen
              export SPRING_DATASOURCE_PASSWORD=citizen
              sudo systemctl start citizen.service || true
              EOF

  tags = {
    Name = "citizen-app-${count.index + 1}"
  }
}

resource "aws_elb" "citizen_lb" {
  name               = "citizen-lb-eu-central-1"
  security_groups    = [aws_security_group.lb_sg.id]
  availability_zones = var.availability_zones

  listener {
    instance_port     = 8080
    instance_protocol = "HTTP"
    lb_port           = 80
    lb_protocol       = "HTTP"
  }

  health_check {
    target              = "HTTP:8080/actuator/health"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }

  instances = [for instance in aws_instance.citizen_app : instance.id]
}

output "load_balancer_dns" {
  value = aws_elb.citizen_lb.dns_name
}
