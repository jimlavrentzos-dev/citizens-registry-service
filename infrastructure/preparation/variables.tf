variable "region" {
  description = "The AWS region to deploy to"
  default     = "eu-central-1"
}

variable "base_ami" {
  description = "Amazon Linux 2 base AMI"
  default     = "ami-0747bdcabd34c712a"
}

variable "public_key_path" {
  description = "Path to your public SSH key (e.g. ~/.ssh/id_rsa.pub)"
  type        = string
}

variable "github_repo_url" {
  description = "Αφορά το URL του GitHub αποθετηρίου που περιέχει το citizen-registry.jar"
  type        = string
}

variable "github_branch" {
  description = "Το Branch που αγορά το checkout"
  type        = string
  default     = "main"
}

variable "db_user" {
  description = "Username for the MySQL database"
  type        = string
  default     = "appuser"
}

variable "db_password" {
  description = "Password for the MySQL database"
  type        = string
  sensitive   =true
}



