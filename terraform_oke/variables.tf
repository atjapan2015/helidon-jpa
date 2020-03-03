// Copyright (c) 2017, 2019, Oracle and/or its affiliates. All rights reserved.

variable "cluster_name" {
  default = "tfWorkshopCluster"
}

variable "availability_domain" {
  default = 3
}

variable "cluster_options_add_ons_is_kubernetes_dashboard_enabled" {
  default = true
}

variable "cluster_options_add_ons_is_tiller_enabled" {
  default = true
}

variable "cluster_options_kubernetes_network_config_pods_cidr" {
  default = "10.1.0.0/16"
}

variable "cluster_options_kubernetes_network_config_services_cidr" {
  default = "10.2.0.0/16"
}

variable "node_pool_initial_node_labels_key" {
  default = "key"
}

variable "node_pool_initial_node_labels_value" {
  default = "value"
}

variable "node_pool_name" {
  default = "tfPool"
}

variable "node_pool_node_image_name" {
  default = "Oracle-Linux-7.6"
}

variable "node_pool_node_shape" {
  default = "VM.Standard2.1"
}

variable "node_pool_quantity_per_subnet" {
  default = 1
}

variable "node_pool_ssh_public_key" {
  default = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC7GhVClvUUHQbrLWt7NuvYrp1yLsvotIGS5TKFDP8+BrOoQNnbAUwVWBJcbDTZV5wsnj5Ot74WVB6e3YYjUo2TIVCPVtCtgezZBpdjfWRiVTiHDY/m3pr/xqoZpwkAFP27dQvXJSEBA6lpLpaDtzdTvW/1zfQEbvDeybdbaNYOgUUNsOrL3o2ShM2P0CVgp6hFaIw+vuMM5yG6is3hZc1WOqVQYfcf/a7Jpuc+MRJZbHYINqENbLv3L/TEyONUtQn/4eHNkwvVunT5Yy8rUxbDfAmFXI37NLlPPPDxtFZ5bkboBZPqjvfRB4UjRzVkPeWqQBgzD71s+xsEs9xnvQyd oracle@k8s-master"
}
