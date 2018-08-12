<h1>1.filesystem integration mongodb gridfs</h1>

<blockquote>mongodb repo http address:
	https://hub.docker.com/_/mongo/</blockquote>

<blockquote>docker repo http address: https://hub.docker.com/r/huasuoworld/filesystem/</blockquote>

<blockquote>docker pull huasuoworld/filesystem</blockquote>

```
docker run -d -p 10010:10010 -e \
JAVA_OPTS="-Dspring.data.mongodb.database=filesystem \
-Dspring.data.mongodb.host=192.168.1.5 \
-Dspring.data.mongodb.username=root \
-Dspring.data.mongodb.password=example \ 
-Dspring.data.mongodb.authentication-database=admin \
-Xms128m -Xmx512m"

```

<h2>2. api description</h2>

```
file upload api: 
	request URL: 
    	http://xxx:10010/api/file/upload 
   request headers: 
   		Content-Type:application/json 
   http request body:
   		{"fullname":"xxx.jpg","base64file":"dasdasdnas"}
   http response body:
                123456.jpg

file download api: 
	request URL: 
    	http://xxx:10010/api/file/download?fullname=123456.jpg
file lookup api: 
    request URL: 
   		http://xxx:10010/api/file/lookup?fullname=123456.jpg
```
