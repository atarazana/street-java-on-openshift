{{/*
Expand the name of the chart.
*/}}
{{- define "fruit-service.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
DB Service name
*/}}
{{- define "fruit-service-db.name" -}}
{{- printf "%s-db" (include "fruit-service.name" .) }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "fruit-service.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create db name 
*/}}
{{- define "fruit-service-db.fullname" -}}
{{- if (include "fruit-service.fullname" .) }}
{{- printf "%s-db" (include "fruit-service.fullname" .) }}
{{- end }}
{{- end }}

{{/*
Create db secret 
*/}}
{{- define "fruit-service-db-secret" -}}
{{- if (include "fruit-service.fullname" .) }}
{{- printf "%s-db-secret" (include "fruit-service.fullname" .) }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "fruit-service.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "fruit-service.labels" -}}
helm.sh/chart: {{ include "fruit-service.chart" . }}
{{ include "fruit-service.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "fruit-service-db.labels" -}}
helm.sh/chart: {{ include "fruit-service.chart" . }}
{{ include "fruit-service-db.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "fruit-service.selectorLabels" -}}
app.kubernetes.io/name: {{ include "fruit-service.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "fruit-service-db.selectorLabels" -}}
app.kubernetes.io/name: {{ include "fruit-service-db.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "fruit-service.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "fruit-service.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}
