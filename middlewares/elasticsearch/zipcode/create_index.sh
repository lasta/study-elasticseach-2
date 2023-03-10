#!/usr/bin/env bash
set -uo pipefail

readonly MAPPING_YAML='mappings.yml'
readonly MAPPING_JSON='mappings.json'
readonly ES_HOST='http://localhost:9200'
readonly INDEX_NAME='zipcode'

function create_mappings_json_file() {
  rm "${MAPPING_JSON}"
  yq . -o json "${MAPPING_YAML}" > "${MAPPING_JSON}"
}

function delete_mappings() {
  curl -X DELETE --location "${ES_HOST}/${INDEX_NAME}?pretty"
}

function create_mappings_into_elasticsearch() {
  curl -X PUT --location "${ES_HOST}/${INDEX_NAME}?pretty" \
    -H "Content-Type: application/json" \
    --data-binary "@${MAPPING_JSON}"
}

function main() {
  create_mappings_json_file || return $?
  delete_mappings  # ignore whether deleting index was failed
  create_mappings_into_elasticsearch || return $?
}

main
