// Copyright (c) 2017, 2019, Oracle and/or its affiliates. All rights reserved.

variable "cluster_kube_config_expiration" {
  default = 2592000
}

variable "cluster_kube_config_token_version" {
  default = "2.0.0"
}

data "oci_containerengine_cluster_kube_config" "workshop_cluster_kube_config" {
  #Required
  cluster_id = oci_containerengine_cluster.workshop_cluster.id

  #Optional
  expiration    = var.cluster_kube_config_expiration
  token_version = var.cluster_kube_config_token_version
}

resource "local_file" "workshop_cluster_kube_config_file" {
  content  = data.oci_containerengine_cluster_kube_config.workshop_cluster_kube_config.content
  filename = "${path.module}/workshop_cluster_kubeconfig"
}
