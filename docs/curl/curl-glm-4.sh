curl -X POST \
        -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsInNpZ25fdHlwZSI6IlNJR04ifQ.eyJhcGlfa2V5IjoiMWI1MTBlMzYyNjE1OWM5NzIxNWRjYmRiODI1ZTAzYjIiLCJleHAiOjE3MjI5NTE5MjAxMjMsInRpbWVzdGFtcCI6MTcyMjk1MDEyMDEyOH0.BHCKq0DSvMQJgz6T9_ySs017qgFHbN9l-8RdqpxNB6w" \
        -H "Content-Type: application/json" \
        -H "User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" \
        -d '{
          "model":"glm-4",
          "stream": "true",
          "messages": [
              {
                  "role": "user",
                  "content": "1+1"
              }
          ]
        }' \
  https://open.bigmodel.cn/api/paas/v4/chat/completions
