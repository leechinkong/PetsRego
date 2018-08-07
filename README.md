# Pets Registration Service
- Cloud build
- GKE
- Spanner
- IAM service account

##### Create cloud build trigger for Github
- Add Github build trigger from GCP Console
  - GCP > Cloud Build > Build Triggers > Add trigger

##### Enable GCP services
<pre>gcloud services enable cloudbuild.googleapis.com</pre>
<pre>gcloud services enable container.googleapis.com</pre>
<pre>gcloud services enable spanner.googleapis.com</pre>

##### Install k8s cmd line, if not available
<pre>gcloud components install kubectl</pre>

##### Setup project ID variable
<pre>export PROJECT_ID=$(gcloud config list --format 'value(core.project)')</pre>

##### Create k8s cluster
<pre>gcloud container clusters create petsrego-cluster --num-nodes=3 --zone=us-central1-a</pre>

##### Create spanner instance and database
<pre>gcloud spanner instances create petsworld --config=regional-us-central1 --nodes=1 --description=petsworld</pre>
<pre>gcloud spanner databases create petsrego --instance=petsworld</pre>
<pre>gcloud spanner databases ddl update petsrego --instance=petsworld --ddl="$(&lt;db/petsrego.ddl)"</pre>

##### Create service account for k8s to use spanner
<pre>gcloud iam service-accounts create petsrego-sa</pre>
<pre>gcloud projects add-iam-policy-binding $ --member serviceAccount:petsrego-sa@${PROJECT_ID}.iam.gserviceaccount.com --role roles/spanner.databaseUser</pre>

##### Export service account key
<pre>gcloud iam service-accounts keys create petsrego-sa-key.json --iam-account petsrego-sa@${PROJECT_ID}.iam.gserviceaccount.com</pre>

##### Import service account key to GKE
<pre>kubectl create secret generic petsrego-sa-key --from-file=petsrego-sa-key.json=petsrego-sa-key.json</pre>

## Additional information

##### Running build manually:
<pre>gcloud builds submit --config cloudbuild.yaml .</pre>
