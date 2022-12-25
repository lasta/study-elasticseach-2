# Zipcode index
## Requirements
* [yq](https://mikefarah.gitbook.io/yq/)
  * To create a mapping with yaml.
    * Elasticsearch requires json format, but it's hard to hand-write.
* [nkf](https://ja.osdn.net/projects/nkf/)
  * To convert from Shift-JIS data.
    * Japan Post creates zip data on Shift-JIS format, but elasticsearch requires on UTF-8 format.
* [Elasticsearch](https://www.elastic.co/jp/elasticsearch/) 8.5.0

## Initialize index
```shell
./create_index.sh
```

## Get and convert data
1. Download zipcode archive from [this page](https://www.post.japanpost.jp/zipcode/dl/kogaki-zip.html).
   * Click "東京都" to download it.
2. Unzip it
3. Convert from Shift-JIS to UTF-8
    ```shell
    nkf -Lu -w 13TOKYO.CSV > utf8_13tokyo.csv
    ```
4. Put converted file this directory.
