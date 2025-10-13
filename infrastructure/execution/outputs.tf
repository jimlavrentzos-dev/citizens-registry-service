output "load_balancer_dns" {
  description = "DNS name of the load balancer exposing the app"
  value       = aws_elb.citizen_lb.dns_name
}
