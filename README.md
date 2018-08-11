# filesystem with mongo gridfs
docker repo http address:
https://hub.docker.com/r/huasuoworld/filesystem/

docker pull huasuoworld/filesystem

file upload api:
  request URL:
    http://xxx:10010/api/file/upload
  request headers:
    Content-Type:application/json
  http request body
{"fullname":"xxx.jpg","base64file":"dasdasdnas"}

file download api:
  request URL:
    http://xxx:10010/api/file/download?fullname=xxx.jpg
file lookup api:
  request URL:
    http://xxx:10010/api/file/lookup?fullname=xxx.jpg
