terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "var.region"
}

resource "aws_key_pair" "citizen_key" {
  key_name   = "citizen-key"
  public_key = file(var.public_key_path)
}

resource "aws_instance" "citizen_image_source" {
  ami           = var.base_ami
  instance_type = "t3.micro"
  key_name      = aws_key_pair.citizen_key.key_name

  provisioner "remote-exec" {
    inline = [
      "sudo yum install -y git java-17-amazon-corretto-headless maven",
      "git clone -b ${var.github_branch} ${var.github_repo_url} /home/ec2-user/citizen-repo",
      "cd /home/ec2-user/citizen-repo",
      "mvn clean package -DskipTests",
      "cp target/citizen-registry.jar /home/ec2-user/citizen-registry.jar",
      "sudo bash -c 'cat > /etc/systemd/system/citizen.service <<EOF\\n[Unit]\\nDescription=Citizen Registry REST API\\n[Service]\\nExecStart=/usr/bin/java -jar /home/ec2-user/citizen-registry.jar\\n[Install]\\nWantedBy=multi-user.target\\nEOF'",
      "sudo systemctl enable citizen.service"
    ]
  }

  tags = {
    Name = "citizen-prep-instance"
  }
}

resource "aws_ami_from_instance" "citizen_ami" {
  name               = "citizen-registry-image-eu-central-1"
  source_instance_id = aws_instance.citizen_image_source.id
  depends_on         = [aws_instance.citizen_image_source]
}

output "citizen_ami_id" {
  description = "It is the AMI image ID we are going to use in the deployment phase"
  value       = aws_ami_from_instance.citizen_ami.id
}
