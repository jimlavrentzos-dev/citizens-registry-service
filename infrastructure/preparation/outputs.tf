output "citizen_ami_id" {
  description = "It is the AMI image ID we are going to use in the deployment phase"
  value       = aws_ami_from_instance.citizen_ami.id
}
