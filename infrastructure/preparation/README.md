# preparation stage 

Aρχικά δημιουργείται ένα EC2 instance. Έπειτα, γίνεται clone το repository (https://github.com/jimlavrentzos-dev/citizens-registry-service) του citizen-registry από το GitHub.
Στην συνέχεια, γίνεται build το jar με Maven, εγκαθίσταται η Java και στο τέλος παράγεται ένα AMI.

## Εντολές στην κονσόλα του Git

Git bash


cd infrastructure/preparation
terraform init
terraform apply -var="public_key_path=~/.ssh/id_rsa.pub" \
terraform apply -var="github_repo_url=https://github.com/jimlavrentzos-dev/citizens-registry-service" \
terraform apply -var="github_branch=main" \
                      -auto-approve