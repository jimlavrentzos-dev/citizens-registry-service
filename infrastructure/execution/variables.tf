variable "region" {
  description = "The AWS region to deploy to"
  default     = "eu-central-1"
}

variable "availability_zones" {
  default = ["eu-central-1a", "eu-central-1b"]
}

variable "key_name" {
  description = "SSH key pair name for EC2 instances"
  type = string
}

variable "base_ami" {
  default = "ami-0747bdcabd34c712a"
}

variable "citizen_ami_id" {
  description = "It is the AMI image ID we are going to use in the deployment phase"
  type = string
}