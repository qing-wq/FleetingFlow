---
title: gateway v1.0.0
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.17"

---

# gateway

> v1.0.0

Base URLs:

* <a href="http://localhost:4000">开发环境: http://localhost:4000</a>

# Authentication

# 用户接口

## GET 用户主页接口

GET /user/{userId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|path|string| 是 |如果不填默认为当前登录用户主页|
|homeSelectType|query|string| 否 |主页选择标签，默认works|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "homeSelectType": "",
    "homeSelectList": {
      "list": [
        {
          "videoId": 0,
          "userId": 0,
          "picture": "",
          "url": "",
          "status": 0,
          "praised": false,
          "priseCount": 0
        }
      ],
      "hasMore": false
    },
    "followList": {
      "list": [
        {
          "relationId": 0,
          "followed": false,
          "userId": 0,
          "userName": "",
          "avatar": ""
        }
      ],
      "hasMore": false
    },
    "userHome": {
      "followCount": 0,
      "fansCount": 0,
      "videoCount": 0,
      "praiseCount": 0,
      "collectionCount": 0,
      "totalReadCount": 0,
      "followed": false,
      "userId": 0,
      "userName": "",
      "role": "",
      "picture": "",
      "profile": "",
      "email": "",
      "id": 0,
      "createTime": "",
      "updateTime": ""
    }
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 用户主页分页接口

GET /user/page

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|query|integer| 否 |如果不填默认为当前登录用户主页|
|homeSelectType|query|string| 否 |主页选择标签，默认works|
|page|query|integer| 是 |none|
|pageSize|query|integer| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "homeSelectType": "",
    "homeSelectList": {
      "list": [
        {
          "videoId": 0,
          "userId": 0,
          "picture": "",
          "url": "",
          "status": 0,
          "praised": false,
          "priseCount": 0
        }
      ],
      "hasMore": false
    },
    "followList": {
      "list": [
        {
          "relationId": 0,
          "followed": false,
          "userId": 0,
          "userName": "",
          "avatar": ""
        }
      ],
      "hasMore": false
    },
    "userHome": {
      "followCount": 0,
      "fansCount": 0,
      "videoCount": 0,
      "praiseCount": 0,
      "collectionCount": 0,
      "totalReadCount": 0,
      "followed": false,
      "userId": 0,
      "userName": "",
      "role": "",
      "picture": "",
      "profile": "",
      "email": "",
      "id": 0,
      "createTime": "",
      "updateTime": ""
    }
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 获取粉丝/关注列表

GET /user/follow/{userId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|path|string| 是 |none|
|type|query|string| 否 |follow-关注，fans-粉丝|
|page|query|integer| 是 |none|
|pageSize|query|integer| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "list": [
      {
        "relationId": 0,
        "followed": false,
        "userId": 0,
        "userName": "",
        "avatar": ""
      }
    ],
    "hasMore": false
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 获取用户信息

GET /user/info

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "userId": 0,
    "userName": "",
    "role": "",
    "picture": "",
    "profile": "",
    "email": "",
    "id": 0,
    "createTime": "",
    "updateTime": ""
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## POST 保存用户信息

POST /user/saveInfo

> Body 请求参数

```json
{
  "userId": 0,
  "userName": "string",
  "picture": "string",
  "email": "string",
  "profile": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[UserInfoSaveReq](#schemauserinfosavereq)| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": false
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## POST 更改密码

POST /user/update

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|old|query|string| 是 |none|
|new|query|string| 是 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

# 登录接口

## POST 账号密码登录

POST /login

> Body 请求参数

```yaml
username: 123@qq.com
password: "123456"

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» username|body|string| 否 |none|
|» password|body|string| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "userId": 0,
    "userName": "",
    "role": "",
    "picture": "",
    "profile": "",
    "email": "",
    "id": 0,
    "createTime": "",
    "updateTime": ""
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 登出接口

GET /logout

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## POST 获取验证码

POST /code

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|email|query|string| 是 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## POST 用户注册

POST /register

> Body 请求参数

```json
{
  "email": "r.ogiqupdjkd@qq.com",
  "username": "test",
  "phone": "18189112823",
  "password": "eaaaaaaa",
  "code": "1234"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[UserSaveReq](#schemausersavereq)| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": 0
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

*commentId*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

# 用户交互接口

## GET 视频点赞、收藏相关操作

GET /foot/favor

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|videoId|query|integer| 是 |none|
|operate|query|integer| 是 |2-点赞 3-收藏 4-取消点赞 5-取消收藏|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": false
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## POST 用户关注

POST /foot/follow

> Body 请求参数

```json
{
  "userId": 0,
  "followUserId": 0,
  "followed": true
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[UserRelationReq](#schemauserrelationreq)| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": false
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

# 评论接口

## GET 评论列表分页接口

GET /comment/api/page

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|videoId|query|integer| 是 |none|
|page|query|integer| 是 |none|
|pageSize|query|integer| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "list": [
      {
        "commentCount": 0,
        "childComments": [
          {
            "parentContent": "",
            "userId": 0,
            "userName": "",
            "userPhoto": "",
            "commentTime": 0,
            "commentContent": "",
            "commentId": 0,
            "praiseCount": 0,
            "praised": false
          }
        ],
        "userId": 0,
        "userName": "",
        "userPhoto": "",
        "commentTime": 0,
        "commentContent": "",
        "commentId": 0,
        "praiseCount": 0,
        "praised": false
      }
    ],
    "hasMore": false
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## POST 发布评论

POST /comment/api/post

> Body 请求参数

```json
{
  "commentContent": "sed tempor pariatur non ad",
  "videoId": 219
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[CommentSaveReq](#schemacommentsavereq)| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": 0
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

*commentId*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## GET 评论点赞、取消点赞等操作

GET /comment/api/favor

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|commentId|query|integer| 是 |none|
|type|query|integer| 是 |2-点赞 4-取消点赞|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

# 视频接口

## GET 视频详情页

GET /video/detail/{videoId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|videoId|path|string| 是 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "video": {
      "videoId": 0,
      "userId": 0,
      "title": "",
      "thumbnail": "",
      "picture": "",
      "categoryId": 0,
      "status": 0,
      "tags": [
        {
          "tagId": 0,
          "tag": "",
          "status": 0,
          "selected": false
        }
      ],
      "praised": false,
      "followed": false,
      "collected": false,
      "url": "",
      "format": "",
      "resolution": "",
      "type": 0,
      "author": {
        "userId": 0,
        "picture": "",
        "userName": ""
      },
      "count": {
        "praiseCount": 0,
        "viewCount": 0,
        "collectionCount": 0,
        "forwardCount": 0,
        "commentCount": 0
      }
    },
    "comments": [
      {
        "commentCount": 0,
        "childComments": [
          {
            "parentContent": "",
            "userId": 0,
            "userName": "",
            "userPhoto": "",
            "commentTime": 0,
            "commentContent": "",
            "commentId": 0,
            "praiseCount": 0,
            "praised": false
          }
        ],
        "userId": 0,
        "userName": "",
        "userPhoto": "",
        "commentTime": 0,
        "commentContent": "",
        "commentId": 0,
        "praiseCount": 0,
        "praised": false
      }
    ],
    "author": {
      "userId": 0,
      "picture": "",
      "userName": ""
    }
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 视频推荐

GET /video/recommend

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|category|query|integer| 是 |none|
|size|query|integer| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "list": [
      {
        "videoId": 0,
        "userId": 0,
        "title": "",
        "thumbnail": "",
        "picture": "",
        "categoryId": 0,
        "status": 0,
        "tags": [
          {
            "tagId": 0,
            "tag": "",
            "status": 0,
            "selected": false
          }
        ],
        "praised": false,
        "followed": false,
        "collected": false,
        "url": "",
        "format": "",
        "resolution": "",
        "type": 0,
        "author": {
          "userId": 0,
          "picture": "",
          "userName": ""
        },
        "count": {
          "praiseCount": 0,
          "viewCount": 0,
          "collectionCount": 0,
          "forwardCount": 0,
          "commentCount": 0
        }
      }
    ],
    "hasMore": false
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## POST 上传视频

POST /video/upload

fixme: 分片上传，断点续传

> Body 请求参数

```yaml
file: string
json: string

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|json|query|string| 否 |其他参数的json封装|
|body|body|object| 否 |none|
|» file|body|string(binary)| 否 |视频文件|
|» json|body|string| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": 0
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

*commentId*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## GET 访问私有文件

GET /video/download/{videoId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|videoId|path|string| 是 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 创建/获取标签id

GET /video/tag

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|tag|query|string| 是 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": 0
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

*commentId*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 上传图片

POST /video/image/upload

todo 图片上传到cdn

> Body 请求参数

```yaml
file: file:///home/qing/Downloads/33df274883e0d2061600b8210411e6f6fa887142.jpg@240w_240h_1c_1s_!web-avatar-space-header.avif

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» file|body|string(binary)| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 视频分享

GET /video/share/{videoId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|videoId|path|string| 是 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 视频删除

GET /video/del/{videoId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|videoId|path|string| 是 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## POST 推荐权重埋点

POST /video/interaction

> Body 请求参数

```json
{
  "videoId": 0,
  "type": 0,
  "data": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[InteractionReq](#schemainteractionreq)| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

# 视频分类、参数接口

## GET 查询所有标签

GET /video/tags/

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|categoryId|path|string| 是 |none|
|page|query|integer| 是 |none|
|pageSize|query|integer| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "list": [
      {
        "tagId": 0,
        "tag": "",
        "status": 0,
        "selected": false
      }
    ],
    "hasMore": false
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 查询所有分类

GET /video/category

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": [
    {
      "EMPTY": {
        "EMPTY": {},
        "categoryId": 0,
        "category": "",
        "rank": 0,
        "status": 0,
        "selected": false
      },
      "categoryId": 0,
      "category": "",
      "rank": 0,
      "status": 0,
      "selected": false
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 查询所有标签

GET /video/tags

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|categoryId|path|string| 是 |none|
|page|query|integer| 是 |none|
|pageSize|query|integer| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "list": [
      {
        "tagId": 0,
        "tag": "",
        "status": 0,
        "selected": false
      }
    ],
    "hasMore": false
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 标签推荐

GET /video/tags/recommend

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|key|query|string| 是 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": [
    ""
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

# 视频搜索接口

## GET 视频搜索

GET /search

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|key|query|string| 是 |关键词|
|page|query|integer| 是 |none|
|size|query|integer| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "list": [
      {
        "id": 0,
        "authorId": 0,
        "title": "",
        "thumbnail": "",
        "picture": "",
        "status": 0,
        "createTime": "",
        "updateTime": ""
      }
    ],
    "hasMore": false
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

# 消息接口

## GET 消息列表

GET /notice/{type}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|type|path|string| 是 |消息类型，如：comment、reply、praise、collect、follow、system|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "list": {
      "list": [
        {
          "msgId": 0,
          "relatedId": "",
          "relatedInfo": "",
          "operateUserId": 0,
          "operateUserName": "",
          "operateUserPhoto": "",
          "type": 0,
          "msg": "",
          "state": 0,
          "createTime": ""
        }
      ],
      "hasMore": false
    },
    "unreadCountMap": {
      "": 0
    },
    "selectType": ""
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET 消息分页接口

GET /notice/page/{type}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|type|path|string| 是 |必需参数|
|page|query|integer| 是 |none|
|pageSize|query|integer| 否 |none|

> 返回示例

> 成功

```json
{
  "status": {
    "code": 0,
    "msg": ""
  },
  "result": {
    "list": [
      {
        "msgId": 0,
        "relatedId": "",
        "relatedInfo": "",
        "operateUserId": 0,
        "operateUserName": "",
        "operateUserPhoto": "",
        "type": 0,
        "msg": "",
        "state": 0,
        "createTime": ""
      }
    ],
    "hasMore": false
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

# 数据模型

<h2 id="tocS_UserRelationReq">UserRelationReq</h2>

<a id="schemauserrelationreq"></a>
<a id="schema_UserRelationReq"></a>
<a id="tocSuserrelationreq"></a>
<a id="tocsuserrelationreq"></a>

```json
{
  "userId": 0,
  "followUserId": 0,
  "followed": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer|false|none||用户ID|
|followUserId|integer|false|none||粉丝用户ID|
|followed|boolean|false|none||是否关注当前用户|

<h2 id="tocS_UserFootDTO">UserFootDTO</h2>

<a id="schemauserfootdto"></a>
<a id="schema_UserFootDTO"></a>
<a id="tocSuserfootdto"></a>
<a id="tocsuserfootdto"></a>

```json
{
  "userId": 0,
  "videoId": 0,
  "type": 0,
  "videoUserId": 0,
  "collectionStat": 0,
  "readStat": 0,
  "commentStat": 0,
  "praiseStat": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer|false|none||用户ID|
|videoId|integer|false|none||视频/评论ID|
|type|integer|false|none||文档类型：1-视频，2-评论<br />{@link ink.whi.common.enums.VideoTypeEnum}|
|videoUserId|integer|false|none||发布该文档的用户ID|
|collectionStat|integer|false|none||收藏状态: 0-未收藏，1-已收藏|
|readStat|integer|false|none||阅读状态: 0-未读，1-已读|
|commentStat|integer|false|none||评论状态: 0-未评论，1-已评论|
|praiseStat|integer|false|none||点赞状态: 0-未点赞，1-已点赞|

<h2 id="tocS_ResVo«BaseUserInfoDTO»">ResVo«BaseUserInfoDTO»</h2>

<a id="schemaresvo«baseuserinfodto»"></a>
<a id="schema_ResVo«BaseUserInfoDTO»"></a>
<a id="tocSresvo«baseuserinfodto»"></a>
<a id="tocsresvo«baseuserinfodto»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "userId": 0,
    "userName": "string",
    "role": "string",
    "picture": "string",
    "profile": "string",
    "email": "string",
    "id": 0,
    "createTime": "string",
    "updateTime": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[BaseUserInfoDTO](#schemabaseuserinfodto)|false|none||none|

<h2 id="tocS_BaseUserInfoDTO">BaseUserInfoDTO</h2>

<a id="schemabaseuserinfodto"></a>
<a id="schema_BaseUserInfoDTO"></a>
<a id="tocSbaseuserinfodto"></a>
<a id="tocsbaseuserinfodto"></a>

```json
{
  "userId": 0,
  "userName": "string",
  "role": "string",
  "picture": "string",
  "profile": "string",
  "email": "string",
  "id": 0,
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer|false|none||用户id|
|userName|string|false|none||用户名|
|role|string|false|none||用户角色 必须是String|
|picture|string|false|none||用户头像|
|profile|string|false|none||个人简介|
|email|string|false|none||邮箱|
|id|integer|false|none||none|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||更新时间|

<h2 id="tocS_UserInfoSaveReq">UserInfoSaveReq</h2>

<a id="schemauserinfosavereq"></a>
<a id="schema_UserInfoSaveReq"></a>
<a id="tocSuserinfosavereq"></a>
<a id="tocsuserinfosavereq"></a>

```json
{
  "userId": 0,
  "userName": "string",
  "picture": "string",
  "email": "string",
  "profile": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer|false|none||用户ID (不需要填)|
|userName|string|false|none||用户名|
|picture|string|false|none||用户头像|
|email|string|false|none||邮箱|
|profile|string|false|none||个人简介|

<h2 id="tocS_ResVo«Boolean»">ResVo«Boolean»</h2>

<a id="schemaresvo«boolean»"></a>
<a id="schema_ResVo«Boolean»"></a>
<a id="tocSresvo«boolean»"></a>
<a id="tocsresvo«boolean»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|boolean|false|none||none|

<h2 id="tocS_UserSaveReq">UserSaveReq</h2>

<a id="schemausersavereq"></a>
<a id="schema_UserSaveReq"></a>
<a id="tocSusersavereq"></a>
<a id="tocsusersavereq"></a>

```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "code": "string",
  "phone": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|username|string|false|none||用户名|
|password|string|false|none||密码|
|email|string|false|none||邮箱|
|code|string|false|none||验证码|
|phone|string|false|none||电话|

<h2 id="tocS_ResVo«PageListVo«FollowUserInfoDTO»»">ResVo«PageListVo«FollowUserInfoDTO»»</h2>

<a id="schemaresvo«pagelistvo«followuserinfodto»»"></a>
<a id="schema_ResVo«PageListVo«FollowUserInfoDTO»»"></a>
<a id="tocSresvo«pagelistvo«followuserinfodto»»"></a>
<a id="tocsresvo«pagelistvo«followuserinfodto»»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "list": [
      {
        "relationId": 0,
        "followed": true,
        "userId": 0,
        "userName": "string",
        "avatar": "string"
      }
    ],
    "hasMore": true
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[PageListVo«FollowUserInfoDTO»](#schemapagelistvo%c2%abfollowuserinfodto%c2%bb)|false|none||ink.whi.common.model.page.PageListVo<ink.whi.user.model.dto.FollowUserInfoDTO>|

<h2 id="tocS_ResVo«UserHomeVo»">ResVo«UserHomeVo»</h2>

<a id="schemaresvo«userhomevo»"></a>
<a id="schema_ResVo«UserHomeVo»"></a>
<a id="tocSresvo«userhomevo»"></a>
<a id="tocsresvo«userhomevo»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "homeSelectType": "string",
    "homeSelectList": {
      "list": [
        {
          "videoId": 0,
          "userId": 0,
          "title": "string",
          "thumbnail": "string",
          "picture": "string",
          "categoryId": 0,
          "status": 0,
          "tags": [
            {
              "tagId": 0,
              "tag": "string",
              "status": 0,
              "selected": true
            }
          ],
          "praised": true,
          "followed": true,
          "collected": true,
          "url": "string",
          "format": "string",
          "resolution": "string",
          "type": 0,
          "author": {
            "userId": 0,
            "picture": "string",
            "userName": "string"
          },
          "count": {
            "praiseCount": 0,
            "viewCount": 0,
            "collectionCount": 0,
            "forwardCount": 0,
            "commentCount": 0
          }
        }
      ],
      "hasMore": true
    },
    "userHome": {
      "followCount": 0,
      "fansCount": 0,
      "videoCount": 0,
      "praiseCount": 0,
      "collectionCount": 0,
      "totalReadCount": 0,
      "followed": true,
      "userId": 0,
      "userName": "string",
      "role": "string",
      "picture": "string",
      "profile": "string",
      "email": "string",
      "id": 0,
      "createTime": "string",
      "updateTime": "string"
    }
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[UserHomeVo](#schemauserhomevo)|false|none||none|

<h2 id="tocS_UserHomeVo">UserHomeVo</h2>

<a id="schemauserhomevo"></a>
<a id="schema_UserHomeVo"></a>
<a id="tocSuserhomevo"></a>
<a id="tocsuserhomevo"></a>

```json
{
  "homeSelectType": "string",
  "homeSelectList": {
    "list": [
      {
        "videoId": 0,
        "userId": 0,
        "title": "string",
        "thumbnail": "string",
        "picture": "string",
        "categoryId": 0,
        "status": 0,
        "tags": [
          {
            "tagId": 0,
            "tag": "string",
            "status": 0,
            "selected": true
          }
        ],
        "praised": true,
        "followed": true,
        "collected": true,
        "url": "string",
        "format": "string",
        "resolution": "string",
        "type": 0,
        "author": {
          "userId": 0,
          "picture": "string",
          "userName": "string"
        },
        "count": {
          "praiseCount": 0,
          "viewCount": 0,
          "collectionCount": 0,
          "forwardCount": 0,
          "commentCount": 0
        }
      }
    ],
    "hasMore": true
  },
  "userHome": {
    "followCount": 0,
    "fansCount": 0,
    "videoCount": 0,
    "praiseCount": 0,
    "collectionCount": 0,
    "totalReadCount": 0,
    "followed": true,
    "userId": 0,
    "userName": "string",
    "role": "string",
    "picture": "string",
    "profile": "string",
    "email": "string",
    "id": 0,
    "createTime": "string",
    "updateTime": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|homeSelectType|string|false|none||用户主页选择标签|
|homeSelectList|[PageListVo«VideoInfoDTO»](#schemapagelistvo%c2%abvideoinfodto%c2%bb)|false|none||视频列表|
|userHome|[UserStatisticInfoDTO](#schemauserstatisticinfodto)|false|none||用户个人信息|

<h2 id="tocS_UserStatisticInfoDTO">UserStatisticInfoDTO</h2>

<a id="schemauserstatisticinfodto"></a>
<a id="schema_UserStatisticInfoDTO"></a>
<a id="tocSuserstatisticinfodto"></a>
<a id="tocsuserstatisticinfodto"></a>

```json
{
  "followCount": 0,
  "fansCount": 0,
  "videoCount": 0,
  "praiseCount": 0,
  "collectionCount": 0,
  "totalReadCount": 0,
  "followed": true,
  "userId": 0,
  "userName": "string",
  "role": "string",
  "picture": "string",
  "profile": "string",
  "email": "string",
  "id": 0,
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|followCount|integer|false|none||关注数|
|fansCount|integer|false|none||粉丝数|
|videoCount|integer|false|none||已发布视频数|
|praiseCount|integer|false|none||点赞数|
|collectionCount|integer|false|none||被收藏数|
|totalReadCount|integer|false|none||视频播放总数|
|followed|boolean|false|none||是否关注当前用户|
|userId|integer|false|none||用户id|
|userName|string|false|none||用户名|
|role|string|false|none||用户角色 必须是String|
|picture|string|false|none||用户头像|
|profile|string|false|none||个人简介|
|email|string|false|none||邮箱|
|id|integer|false|none||none|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||更新时间|

<h2 id="tocS_PageListVo«FollowUserInfoDTO»">PageListVo«FollowUserInfoDTO»</h2>

<a id="schemapagelistvo«followuserinfodto»"></a>
<a id="schema_PageListVo«FollowUserInfoDTO»"></a>
<a id="tocSpagelistvo«followuserinfodto»"></a>
<a id="tocspagelistvo«followuserinfodto»"></a>

```json
{
  "list": [
    {
      "relationId": 0,
      "followed": true,
      "userId": 0,
      "userName": "string",
      "avatar": "string"
    }
  ],
  "hasMore": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|list|[[FollowUserInfoDTO](#schemafollowuserinfodto)]|false|none||分页列表|
|hasMore|boolean|false|none||是否有更多|

<h2 id="tocS_FollowUserInfoDTO">FollowUserInfoDTO</h2>

<a id="schemafollowuserinfodto"></a>
<a id="schema_FollowUserInfoDTO"></a>
<a id="tocSfollowuserinfodto"></a>
<a id="tocsfollowuserinfodto"></a>

```json
{
  "relationId": 0,
  "followed": true,
  "userId": 0,
  "userName": "string",
  "avatar": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|relationId|integer|false|none||当前登录的用户与这个用户之间的关联关系id|
|followed|boolean|false|none||标识当前登录用户有没有关注这个用户|
|userId|integer|false|none||用户id|
|userName|string|false|none||用户名|
|avatar|string|false|none||用户头像|

<h2 id="tocS_PageListVo«SimpleVideoInfoDTO»">PageListVo«SimpleVideoInfoDTO»</h2>

<a id="schemapagelistvo«simplevideoinfodto»"></a>
<a id="schema_PageListVo«SimpleVideoInfoDTO»"></a>
<a id="tocSpagelistvo«simplevideoinfodto»"></a>
<a id="tocspagelistvo«simplevideoinfodto»"></a>

```json
{
  "list": [
    {
      "videoId": 0,
      "authorId": 0,
      "picture": "string",
      "url": "string",
      "status": 0,
      "praised": true,
      "priseCount": 0
    }
  ],
  "hasMore": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|list|[[SimpleVideoInfoDTO](#schemasimplevideoinfodto)]|false|none||分页列表|
|hasMore|boolean|false|none||是否有更多|

<h2 id="tocS_SimpleVideoInfoDTO">SimpleVideoInfoDTO</h2>

<a id="schemasimplevideoinfodto"></a>
<a id="schema_SimpleVideoInfoDTO"></a>
<a id="tocSsimplevideoinfodto"></a>
<a id="tocssimplevideoinfodto"></a>

```json
{
  "videoId": 0,
  "authorId": 0,
  "picture": "string",
  "url": "string",
  "status": 0,
  "praised": true,
  "priseCount": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|videoId|integer|false|none||视频id|
|authorId|integer|false|none||作者id|
|picture|string|false|none||视频头图|
|url|string|false|none||视频地址|
|status|integer|false|none||状态：0-未发布,1-已发布,2-待审核|
|praised|boolean|false|none||表示当前查看的用户是否已经点赞过|
|priseCount|integer|false|none||视频点赞数|

<h2 id="tocS_ResVo«PageListVo«TagDTO»»">ResVo«PageListVo«TagDTO»»</h2>

<a id="schemaresvo«pagelistvo«tagdto»»"></a>
<a id="schema_ResVo«PageListVo«TagDTO»»"></a>
<a id="tocSresvo«pagelistvo«tagdto»»"></a>
<a id="tocsresvo«pagelistvo«tagdto»»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "list": [
      {
        "tagId": 0,
        "tag": "string",
        "status": 0,
        "selected": true
      }
    ],
    "hasMore": true
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[PageListVo«TagDTO»](#schemapagelistvo%c2%abtagdto%c2%bb)|false|none||ink.whi.common.model.page.PageListVo<ink.whi.video.dto.TagDTO>|

<h2 id="tocS_PageListVo«TagDTO»">PageListVo«TagDTO»</h2>

<a id="schemapagelistvo«tagdto»"></a>
<a id="schema_PageListVo«TagDTO»"></a>
<a id="tocSpagelistvo«tagdto»"></a>
<a id="tocspagelistvo«tagdto»"></a>

```json
{
  "list": [
    {
      "tagId": 0,
      "tag": "string",
      "status": 0,
      "selected": true
    }
  ],
  "hasMore": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|list|[[TagDTO](#schematagdto)]|false|none||分页列表|
|hasMore|boolean|false|none||是否有更多|

<h2 id="tocS_ResVo«PageListVo«VideoInfoDTO»»">ResVo«PageListVo«VideoInfoDTO»»</h2>

<a id="schemaresvo«pagelistvo«videoinfodto»»"></a>
<a id="schema_ResVo«PageListVo«VideoInfoDTO»»"></a>
<a id="tocSresvo«pagelistvo«videoinfodto»»"></a>
<a id="tocsresvo«pagelistvo«videoinfodto»»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "list": [
      {
        "videoId": 0,
        "userId": 0,
        "title": "string",
        "thumbnail": "string",
        "picture": "string",
        "categoryId": 0,
        "status": 0,
        "tags": [
          {
            "tagId": 0,
            "tag": "string",
            "status": 0,
            "selected": true
          }
        ],
        "praised": true,
        "followed": true,
        "collected": true,
        "url": "string",
        "format": "string",
        "resolution": "string",
        "type": 0,
        "author": {
          "userId": 0,
          "picture": "string",
          "userName": "string"
        },
        "count": {
          "praiseCount": 0,
          "viewCount": 0,
          "collectionCount": 0,
          "forwardCount": 0,
          "commentCount": 0
        }
      }
    ],
    "hasMore": true
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[PageListVo«VideoInfoDTO»](#schemapagelistvo%c2%abvideoinfodto%c2%bb)|false|none||视频列表|

<h2 id="tocS_PageListVo«VideoInfoDTO»">PageListVo«VideoInfoDTO»</h2>

<a id="schemapagelistvo«videoinfodto»"></a>
<a id="schema_PageListVo«VideoInfoDTO»"></a>
<a id="tocSpagelistvo«videoinfodto»"></a>
<a id="tocspagelistvo«videoinfodto»"></a>

```json
{
  "list": [
    {
      "videoId": 0,
      "userId": 0,
      "title": "string",
      "thumbnail": "string",
      "picture": "string",
      "categoryId": 0,
      "status": 0,
      "tags": [
        {
          "tagId": 0,
          "tag": "string",
          "status": 0,
          "selected": true
        }
      ],
      "praised": true,
      "followed": true,
      "collected": true,
      "url": "string",
      "format": "string",
      "resolution": "string",
      "type": 0,
      "author": {
        "userId": 0,
        "picture": "string",
        "userName": "string"
      },
      "count": {
        "praiseCount": 0,
        "viewCount": 0,
        "collectionCount": 0,
        "forwardCount": 0,
        "commentCount": 0
      }
    }
  ],
  "hasMore": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|list|[[VideoInfoDTO](#schemavideoinfodto)]|false|none||分页列表|
|hasMore|boolean|false|none||是否有更多|

<h2 id="tocS_ResVo«VideoDetailVO»">ResVo«VideoDetailVO»</h2>

<a id="schemaresvo«videodetailvo»"></a>
<a id="schema_ResVo«VideoDetailVO»"></a>
<a id="tocSresvo«videodetailvo»"></a>
<a id="tocsresvo«videodetailvo»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "video": {
      "videoId": 0,
      "userId": 0,
      "title": "string",
      "thumbnail": "string",
      "picture": "string",
      "categoryId": 0,
      "status": 0,
      "tags": [
        {
          "tagId": 0,
          "tag": "string",
          "status": 0,
          "selected": true
        }
      ],
      "praised": true,
      "followed": true,
      "collected": true,
      "url": "string",
      "format": "string",
      "resolution": "string",
      "type": 0,
      "author": {
        "userId": 0,
        "picture": "string",
        "userName": "string"
      },
      "count": {
        "praiseCount": 0,
        "viewCount": 0,
        "collectionCount": 0,
        "forwardCount": 0,
        "commentCount": 0
      }
    },
    "comments": [
      {
        "commentCount": 0,
        "childComments": [
          {
            "parentContent": null,
            "userId": null,
            "userName": null,
            "userPhoto": null,
            "commentTime": null,
            "commentContent": null,
            "commentId": null,
            "praiseCount": null,
            "praised": null
          }
        ],
        "userId": 0,
        "userName": "string",
        "userPhoto": "string",
        "commentTime": 0,
        "commentContent": "string",
        "commentId": 0,
        "praiseCount": 0,
        "praised": true
      }
    ],
    "author": {
      "userId": 0,
      "picture": "string",
      "userName": "string"
    }
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[VideoDetailVO](#schemavideodetailvo)|false|none||none|

<h2 id="tocS_VideoDetailVO">VideoDetailVO</h2>

<a id="schemavideodetailvo"></a>
<a id="schema_VideoDetailVO"></a>
<a id="tocSvideodetailvo"></a>
<a id="tocsvideodetailvo"></a>

```json
{
  "video": {
    "videoId": 0,
    "userId": 0,
    "title": "string",
    "thumbnail": "string",
    "picture": "string",
    "categoryId": 0,
    "status": 0,
    "tags": [
      {
        "tagId": 0,
        "tag": "string",
        "status": 0,
        "selected": true
      }
    ],
    "praised": true,
    "followed": true,
    "collected": true,
    "url": "string",
    "format": "string",
    "resolution": "string",
    "type": 0,
    "author": {
      "userId": 0,
      "picture": "string",
      "userName": "string"
    },
    "count": {
      "praiseCount": 0,
      "viewCount": 0,
      "collectionCount": 0,
      "forwardCount": 0,
      "commentCount": 0
    }
  },
  "comments": [
    {
      "commentCount": 0,
      "childComments": [
        {
          "parentContent": "string",
          "userId": 0,
          "userName": "string",
          "userPhoto": "string",
          "commentTime": 0,
          "commentContent": "string",
          "commentId": 0,
          "praiseCount": 0,
          "praised": true
        }
      ],
      "userId": 0,
      "userName": "string",
      "userPhoto": "string",
      "commentTime": 0,
      "commentContent": "string",
      "commentId": 0,
      "praiseCount": 0,
      "praised": true
    }
  ],
  "author": {
    "userId": 0,
    "picture": "string",
    "userName": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|video|[VideoInfoDTO](#schemavideoinfodto)|false|none||none|
|comments|[[TopCommentDTO1](#schematopcommentdto1)]|false|none||评论信息|
|author|[SimpleUserInfoDTO](#schemasimpleuserinfodto)|false|none||作者相关信息|

<h2 id="tocS_TopCommentDTO">TopCommentDTO</h2>

<a id="schematopcommentdto"></a>
<a id="schema_TopCommentDTO"></a>
<a id="tocStopcommentdto"></a>
<a id="tocstopcommentdto"></a>

```json
{
  "commentCount": 0,
  "childComments": [
    {
      "parentContent": "string",
      "userId": 0,
      "userName": "string",
      "userPhoto": "string",
      "commentTime": 0,
      "commentContent": "string",
      "commentId": 0,
      "praiseCount": 0,
      "praised": true
    }
  ],
  "userId": 0,
  "userName": "string",
  "userPhoto": "string",
  "commentTime": 0,
  "commentContent": "string",
  "commentId": 0,
  "praiseCount": 0,
  "praised": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|commentCount|integer|false|none||评论数量|
|childComments|[[SubCommentDTO](#schemasubcommentdto)]|false|none||子评论|
|userId|integer|false|none||用户ID|
|userName|string|false|none||用户名|
|userPhoto|string|false|none||用户图像|
|commentTime|integer|false|none||评论时间|
|commentContent|string|false|none||评论内容|
|commentId|integer|false|none||评论id|
|praiseCount|integer|false|none||点赞数量|
|praised|boolean|false|none||true 表示已经点赞|

<h2 id="tocS_SubCommentDTO">SubCommentDTO</h2>

<a id="schemasubcommentdto"></a>
<a id="schema_SubCommentDTO"></a>
<a id="tocSsubcommentdto"></a>
<a id="tocssubcommentdto"></a>

```json
{
  "parentContent": "string",
  "userId": 0,
  "userName": "string",
  "userPhoto": "string",
  "commentTime": 0,
  "commentContent": "string",
  "commentId": 0,
  "praiseCount": 0,
  "praised": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|parentContent|string|false|none||父评论内容|
|userId|integer|false|none||用户ID|
|userName|string|false|none||用户名|
|userPhoto|string|false|none||用户图像|
|commentTime|integer|false|none||评论时间|
|commentContent|string|false|none||评论内容|
|commentId|integer|false|none||评论id|
|praiseCount|integer|false|none||点赞数量|
|praised|boolean|false|none||true 表示已经点赞|

<h2 id="tocS_VideoInfoDTO">VideoInfoDTO</h2>

<a id="schemavideoinfodto"></a>
<a id="schema_VideoInfoDTO"></a>
<a id="tocSvideoinfodto"></a>
<a id="tocsvideoinfodto"></a>

```json
{
  "videoId": 0,
  "userId": 0,
  "title": "string",
  "thumbnail": "string",
  "picture": "string",
  "categoryId": 0,
  "status": 0,
  "tags": [
    {
      "tagId": 0,
      "tag": "string",
      "status": 0,
      "selected": true
    }
  ],
  "praised": true,
  "followed": true,
  "collected": true,
  "url": "string",
  "format": "string",
  "resolution": "string",
  "type": 0,
  "author": {
    "userId": 0,
    "picture": "string",
    "userName": "string"
  },
  "count": {
    "praiseCount": 0,
    "viewCount": 0,
    "collectionCount": 0,
    "forwardCount": 0,
    "commentCount": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|videoId|integer|false|none||视频id|
|userId|integer|false|none||作者id|
|title|string|false|none||视频标题|
|thumbnail|string|false|none||视频介绍|
|picture|string|false|none||视频头图|
|categoryId|integer|false|none||分类|
|status|integer|false|none||状态：0-未发布,1-已发布,2-待审核|
|tags|[[TagDTO](#schematagdto)]|false|none||标签|
|praised|boolean|false|none||表示当前查看的用户是否已经点赞过|
|followed|boolean|false|none||表示当用户是否评论过|
|collected|boolean|false|none||表示当前用户是否收藏过|
|url|string|false|none||视频地址|
|format|string|false|none||编码格式|
|resolution|string|false|none||分辨率|
|type|integer|false|none||文件类型<br />{@link FileTypeEnum}|
|author|[SimpleUserInfoDTO](#schemasimpleuserinfodto)|false|none||作者相关信息|
|count|[VideoStatisticDTO](#schemavideostatisticdto)|false|none||视频对应的统计计数|

<h2 id="tocS_VideoStatisticDTO">VideoStatisticDTO</h2>

<a id="schemavideostatisticdto"></a>
<a id="schema_VideoStatisticDTO"></a>
<a id="tocSvideostatisticdto"></a>
<a id="tocsvideostatisticdto"></a>

```json
{
  "praiseCount": 0,
  "viewCount": 0,
  "collectionCount": 0,
  "forwardCount": 0,
  "commentCount": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|praiseCount|integer|false|none||视频点赞数|
|viewCount|integer|false|none||视频播放数|
|collectionCount|integer|false|none||视频被收藏数|
|forwardCount|integer|false|none||转发数|
|commentCount|integer|false|none||评论数|

<h2 id="tocS_SimpleUserInfoDTO">SimpleUserInfoDTO</h2>

<a id="schemasimpleuserinfodto"></a>
<a id="schema_SimpleUserInfoDTO"></a>
<a id="tocSsimpleuserinfodto"></a>
<a id="tocssimpleuserinfodto"></a>

```json
{
  "userId": 0,
  "picture": "string",
  "userName": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer|false|none||用户id|
|picture|string|false|none||作者头像|
|userName|string|false|none||作者名|

<h2 id="tocS_TagDTO">TagDTO</h2>

<a id="schematagdto"></a>
<a id="schema_TagDTO"></a>
<a id="tocStagdto"></a>
<a id="tocstagdto"></a>

```json
{
  "tagId": 0,
  "tag": "string",
  "status": 0,
  "selected": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|tagId|integer|false|none||标签id|
|tag|string|false|none||标签名称|
|status|integer|false|none||0-未发布,1-已发布|
|selected|boolean|false|none||1-被选择|

<h2 id="tocS_ResponseEntity«Object»">ResponseEntity«Object»</h2>

<a id="schemaresponseentity«object»"></a>
<a id="schema_ResponseEntity«Object»"></a>
<a id="tocSresponseentity«object»"></a>
<a id="tocsresponseentity«object»"></a>

```json
{}

```

### 属性

*None*

<h2 id="tocS_InteractionReq">InteractionReq</h2>

<a id="schemainteractionreq"></a>
<a id="schema_InteractionReq"></a>
<a id="tocSinteractionreq"></a>
<a id="tocsinteractionreq"></a>

```json
{
  "videoId": 0,
  "type": 0,
  "data": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|videoId|integer|false|none||none|
|type|integer|false|none||none|
|data|integer|false|none||none|

<h2 id="tocS_ResVo«PageListVo«QiniuContent»»">ResVo«PageListVo«QiniuContent»»</h2>

<a id="schemaresvo«pagelistvo«qiniucontent»»"></a>
<a id="schema_ResVo«PageListVo«QiniuContent»»"></a>
<a id="tocSresvo«pagelistvo«qiniucontent»»"></a>
<a id="tocsresvo«pagelistvo«qiniucontent»»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "list": [
      {
        "id": 0,
        "keyName": "string",
        "bucket": "string",
        "size": "string",
        "url": "string",
        "suffix": "string",
        "type": "公开",
        "updateTime": "string"
      }
    ],
    "hasMore": true
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[PageListVo«QiniuContent»](#schemapagelistvo%c2%abqiniucontent%c2%bb)|false|none||ink.whi.common.model.page.PageListVo<ink.whi.video.repo.qiniu.entity.QiniuContent>|

<h2 id="tocS_PageListVo«QiniuContent»">PageListVo«QiniuContent»</h2>

<a id="schemapagelistvo«qiniucontent»"></a>
<a id="schema_PageListVo«QiniuContent»"></a>
<a id="tocSpagelistvo«qiniucontent»"></a>
<a id="tocspagelistvo«qiniucontent»"></a>

```json
{
  "list": [
    {
      "id": 0,
      "keyName": "string",
      "bucket": "string",
      "size": "string",
      "url": "string",
      "suffix": "string",
      "type": "公开",
      "updateTime": "string"
    }
  ],
  "hasMore": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|list|[[QiniuContent](#schemaqiniucontent)]|false|none||分页列表|
|hasMore|boolean|false|none||是否有更多|

<h2 id="tocS_QiniuContent">QiniuContent</h2>

<a id="schemaqiniucontent"></a>
<a id="schema_QiniuContent"></a>
<a id="tocSqiniucontent"></a>
<a id="tocsqiniucontent"></a>

```json
{
  "id": 0,
  "keyName": "string",
  "bucket": "string",
  "size": "string",
  "url": "string",
  "suffix": "string",
  "type": "公开",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer|false|none||none|
|keyName|string|false|none||文件名|
|bucket|string|false|none||空间名|
|size|string|false|none||大小|
|url|string|false|none||文件地址|
|suffix|string|false|none||文件类型|
|type|string|false|none||空间类型：公开/私有|
|updateTime|string|false|none||创建或更新时间|

<h2 id="tocS_ResVo«QiniuConfig»">ResVo«QiniuConfig»</h2>

<a id="schemaresvo«qiniuconfig»"></a>
<a id="schema_ResVo«QiniuConfig»"></a>
<a id="tocSresvo«qiniuconfig»"></a>
<a id="tocsresvo«qiniuconfig»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "id": 0,
    "accessKey": "string",
    "secretKey": "string",
    "bucket": "string",
    "zone": "string",
    "host": "string",
    "type": "公开"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[QiniuConfig](#schemaqiniuconfig)|false|none||七牛云对象存储配置类|

<h2 id="tocS_QiniuConfig">QiniuConfig</h2>

<a id="schemaqiniuconfig"></a>
<a id="schema_QiniuConfig"></a>
<a id="tocSqiniuconfig"></a>
<a id="tocsqiniuconfig"></a>

```json
{
  "id": 0,
  "accessKey": "string",
  "secretKey": "string",
  "bucket": "string",
  "zone": "string",
  "host": "string",
  "type": "公开"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer|false|none||业务id|
|accessKey|string|false|none||accessKey|
|secretKey|string|false|none||secretKey|
|bucket|string|false|none||存储空间名称作为唯一的 Bucket 识别符|
|zone|string|false|none||Zone表示与机房的对应关系<br />华东	Zone.zone0()<br />华北	Zone.zone1()<br />华南	Zone.zone2()<br />北美	Zone.zoneNa0()<br />东南亚	Zone.zoneAs0()|
|host|string|false|none||外链域名，可自定义，需在七牛云绑定|
|type|string|false|none||空间类型：公开/私有|

<h2 id="tocS_CommentDTO">CommentDTO</h2>

<a id="schemacommentdto"></a>
<a id="schema_CommentDTO"></a>
<a id="tocScommentdto"></a>
<a id="tocscommentdto"></a>

```json
{
  "videoId": 0,
  "userId": 0,
  "content": "string",
  "parentCommentId": 0,
  "topCommentId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|videoId|integer|false|none||视频ID|
|userId|integer|false|none||用户ID|
|content|string|false|none||评论内容|
|parentCommentId|integer|false|none||父评论ID|
|topCommentId|integer|false|none||顶级评论ID|

<h2 id="tocS_ResVo«String»">ResVo«String»</h2>

<a id="schemaresvo«string»"></a>
<a id="schema_ResVo«String»"></a>
<a id="tocSresvo«string»"></a>
<a id="tocsresvo«string»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|string|false|none||none|

<h2 id="tocS_ResVo«List«String»»">ResVo«List«String»»</h2>

<a id="schemaresvo«list«string»»"></a>
<a id="schema_ResVo«List«String»»"></a>
<a id="tocSresvo«list«string»»"></a>
<a id="tocsresvo«list«string»»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": [
    "string"
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[string]|false|none||none|

<h2 id="tocS_ResVo«PageListVo«NotifyMsgDTO»»">ResVo«PageListVo«NotifyMsgDTO»»</h2>

<a id="schemaresvo«pagelistvo«notifymsgdto»»"></a>
<a id="schema_ResVo«PageListVo«NotifyMsgDTO»»"></a>
<a id="tocSresvo«pagelistvo«notifymsgdto»»"></a>
<a id="tocsresvo«pagelistvo«notifymsgdto»»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "list": [
      {
        "msgId": 0,
        "relatedId": "string",
        "relatedInfo": "string",
        "operateUserId": 0,
        "operateUserName": "string",
        "operateUserPhoto": "string",
        "type": 0,
        "msg": "string",
        "state": 0,
        "createTime": "string"
      }
    ],
    "hasMore": true
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[PageListVo«NotifyMsgDTO»](#schemapagelistvo%c2%abnotifymsgdto%c2%bb)|false|none||ink.whi.common.model.page.PageListVo<ink.whi.common.model.dto.NotifyMsgDTO>|

<h2 id="tocS_CommentSaveReq">CommentSaveReq</h2>

<a id="schemacommentsavereq"></a>
<a id="schema_CommentSaveReq"></a>
<a id="tocScommentsavereq"></a>
<a id="tocscommentsavereq"></a>

```json
{
  "commentId": 0,
  "videoId": 0,
  "userId": 0,
  "commentContent": "string",
  "parentCommentId": 0,
  "topCommentId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|commentId|integer|false|none||评论ID(非必须)|
|videoId|integer|false|none||视频ID|
|userId|integer|false|none||用户ID|
|commentContent|string|false|none||评论内容|
|parentCommentId|integer|false|none||父评论ID|
|topCommentId|integer|false|none||顶级评论ID|

<h2 id="tocS_ResVo«NoticeResVo»">ResVo«NoticeResVo»</h2>

<a id="schemaresvo«noticeresvo»"></a>
<a id="schema_ResVo«NoticeResVo»"></a>
<a id="tocSresvo«noticeresvo»"></a>
<a id="tocsresvo«noticeresvo»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "list": {
      "list": [
        {
          "msgId": 0,
          "relatedId": "string",
          "relatedInfo": "string",
          "operateUserId": 0,
          "operateUserName": "string",
          "operateUserPhoto": "string",
          "type": 0,
          "msg": "string",
          "state": 0,
          "createTime": "string"
        }
      ],
      "hasMore": true
    },
    "unreadCountMap": {
      "key": 0
    },
    "selectType": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[NoticeResVo](#schemanoticeresvo)|false|none||none|

<h2 id="tocS_ResVo«Long»">ResVo«Long»</h2>

<a id="schemaresvo«long»"></a>
<a id="schema_ResVo«Long»"></a>
<a id="tocSresvo«long»"></a>
<a id="tocsresvo«long»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|integer|false|none||none|

<h2 id="tocS_NoticeResVo">NoticeResVo</h2>

<a id="schemanoticeresvo"></a>
<a id="schema_NoticeResVo"></a>
<a id="tocSnoticeresvo"></a>
<a id="tocsnoticeresvo"></a>

```json
{
  "list": {
    "list": [
      {
        "msgId": 0,
        "relatedId": "string",
        "relatedInfo": "string",
        "operateUserId": 0,
        "operateUserName": "string",
        "operateUserPhoto": "string",
        "type": 0,
        "msg": "string",
        "state": 0,
        "createTime": "string"
      }
    ],
    "hasMore": true
  },
  "unreadCountMap": {
    "key": 0
  },
  "selectType": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|list|[PageListVo«NotifyMsgDTO»](#schemapagelistvo%c2%abnotifymsgdto%c2%bb)|false|none||ink.whi.common.model.page.PageListVo<ink.whi.common.model.dto.NotifyMsgDTO>|
|unreadCountMap|[Map«Integer»](#schemamap%c2%abinteger%c2%bb)|false|none||每个分类的未读数量|
|selectType|string|false|none||当前选中的消息类型|

<h2 id="tocS_ResVo«PageListVo«TopCommentDTO»»">ResVo«PageListVo«TopCommentDTO»»</h2>

<a id="schemaresvo«pagelistvo«topcommentdto»»"></a>
<a id="schema_ResVo«PageListVo«TopCommentDTO»»"></a>
<a id="tocSresvo«pagelistvo«topcommentdto»»"></a>
<a id="tocsresvo«pagelistvo«topcommentdto»»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "list": [
      {
        "commentCount": 0,
        "childComments": [
          {
            "parentContent": "string",
            "userId": 0,
            "userName": "string",
            "userPhoto": "string",
            "commentTime": 0,
            "commentContent": "string",
            "commentId": 0,
            "praiseCount": 0,
            "praised": true
          }
        ],
        "userId": 0,
        "userName": "string",
        "userPhoto": "string",
        "commentTime": 0,
        "commentContent": "string",
        "commentId": 0,
        "praiseCount": 0,
        "praised": true
      }
    ],
    "hasMore": true
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[PageListVo«TopCommentDTO»](#schemapagelistvo%c2%abtopcommentdto%c2%bb)|false|none||ink.whi.common.model.page.PageListVo<ink.whi.comment.model.dto.TopCommentDTO>|

<h2 id="tocS_Map«Integer»">Map«Integer»</h2>

<a id="schemamap«integer»"></a>
<a id="schema_Map«Integer»"></a>
<a id="tocSmap«integer»"></a>
<a id="tocsmap«integer»"></a>

```json
{
  "key": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|key|integer|false|none||none|

<h2 id="tocS_ResVo«PageListVo«VideoDoc»»">ResVo«PageListVo«VideoDoc»»</h2>

<a id="schemaresvo«pagelistvo«videodoc»»"></a>
<a id="schema_ResVo«PageListVo«VideoDoc»»"></a>
<a id="tocSresvo«pagelistvo«videodoc»»"></a>
<a id="tocsresvo«pagelistvo«videodoc»»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": {
    "list": [
      {
        "id": 0,
        "authorId": 0,
        "title": "string",
        "thumbnail": "string",
        "picture": "string",
        "status": 0,
        "createTime": "string",
        "updateTime": "string"
      }
    ],
    "hasMore": true
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[PageListVo«VideoDoc»](#schemapagelistvo%c2%abvideodoc%c2%bb)|false|none||ink.whi.common.model.page.PageListVo<ink.whi.video.search.repo.entity.VideoDoc>|

<h2 id="tocS_PageListVo«TopCommentDTO»">PageListVo«TopCommentDTO»</h2>

<a id="schemapagelistvo«topcommentdto»"></a>
<a id="schema_PageListVo«TopCommentDTO»"></a>
<a id="tocSpagelistvo«topcommentdto»"></a>
<a id="tocspagelistvo«topcommentdto»"></a>

```json
{
  "list": [
    {
      "commentCount": 0,
      "childComments": [
        {
          "parentContent": "string",
          "userId": 0,
          "userName": "string",
          "userPhoto": "string",
          "commentTime": 0,
          "commentContent": "string",
          "commentId": 0,
          "praiseCount": 0,
          "praised": true
        }
      ],
      "userId": 0,
      "userName": "string",
      "userPhoto": "string",
      "commentTime": 0,
      "commentContent": "string",
      "commentId": 0,
      "praiseCount": 0,
      "praised": true
    }
  ],
  "hasMore": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|list|[[TopCommentDTO1](#schematopcommentdto1)]|false|none||分页列表|
|hasMore|boolean|false|none||是否有更多|

<h2 id="tocS_ResVo«List«CategoryDTO»»">ResVo«List«CategoryDTO»»</h2>

<a id="schemaresvo«list«categorydto»»"></a>
<a id="schema_ResVo«List«CategoryDTO»»"></a>
<a id="tocSresvo«list«categorydto»»"></a>
<a id="tocsresvo«list«categorydto»»"></a>

```json
{
  "status": {
    "code": 0,
    "msg": "string"
  },
  "result": [
    {
      "EMPTY": {
        "EMPTY": {
          "EMPTY": {},
          "categoryId": 0,
          "category": "string",
          "rank": 0,
          "status": 0,
          "selected": true
        },
        "categoryId": 0,
        "category": "string",
        "rank": 0,
        "status": 0,
        "selected": true
      },
      "categoryId": 0,
      "category": "string",
      "rank": 0,
      "status": 0,
      "selected": true
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|[Status](#schemastatus)|false|none||none|
|result|[[CategoryDTO](#schemacategorydto)]|false|none||none|

<h2 id="tocS_PageListVo«NotifyMsgDTO»">PageListVo«NotifyMsgDTO»</h2>

<a id="schemapagelistvo«notifymsgdto»"></a>
<a id="schema_PageListVo«NotifyMsgDTO»"></a>
<a id="tocSpagelistvo«notifymsgdto»"></a>
<a id="tocspagelistvo«notifymsgdto»"></a>

```json
{
  "list": [
    {
      "msgId": 0,
      "relatedId": "string",
      "relatedInfo": "string",
      "operateUserId": 0,
      "operateUserName": "string",
      "operateUserPhoto": "string",
      "type": 0,
      "msg": "string",
      "state": 0,
      "createTime": "string"
    }
  ],
  "hasMore": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|list|[[NotifyMsgDTO](#schemanotifymsgdto)]|false|none||分页列表|
|hasMore|boolean|false|none||是否有更多|

<h2 id="tocS_PageListVo«VideoDoc»">PageListVo«VideoDoc»</h2>

<a id="schemapagelistvo«videodoc»"></a>
<a id="schema_PageListVo«VideoDoc»"></a>
<a id="tocSpagelistvo«videodoc»"></a>
<a id="tocspagelistvo«videodoc»"></a>

```json
{
  "list": [
    {
      "id": 0,
      "authorId": 0,
      "title": "string",
      "thumbnail": "string",
      "picture": "string",
      "status": 0,
      "createTime": "string",
      "updateTime": "string"
    }
  ],
  "hasMore": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|list|[[VideoDoc](#schemavideodoc)]|false|none||分页列表|
|hasMore|boolean|false|none||是否有更多|

<h2 id="tocS_TopCommentDTO1">TopCommentDTO1</h2>

<a id="schematopcommentdto1"></a>
<a id="schema_TopCommentDTO1"></a>
<a id="tocStopcommentdto1"></a>
<a id="tocstopcommentdto1"></a>

```json
{
  "commentCount": 0,
  "childComments": [
    {
      "parentContent": "string",
      "userId": 0,
      "userName": "string",
      "userPhoto": "string",
      "commentTime": 0,
      "commentContent": "string",
      "commentId": 0,
      "praiseCount": 0,
      "praised": true
    }
  ],
  "userId": 0,
  "userName": "string",
  "userPhoto": "string",
  "commentTime": 0,
  "commentContent": "string",
  "commentId": 0,
  "praiseCount": 0,
  "praised": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|commentCount|integer|false|none||评论数量|
|childComments|[[SubCommentDTO1](#schemasubcommentdto1)]|false|none||子评论|
|userId|integer|false|none||用户ID|
|userName|string|false|none||用户名|
|userPhoto|string|false|none||用户图像|
|commentTime|integer|false|none||评论时间|
|commentContent|string|false|none||评论内容|
|commentId|integer|false|none||评论id|
|praiseCount|integer|false|none||点赞数量|
|praised|boolean|false|none||true 表示当前用户点赞|

<h2 id="tocS_CategoryDTO">CategoryDTO</h2>

<a id="schemacategorydto"></a>
<a id="schema_CategoryDTO"></a>
<a id="tocScategorydto"></a>
<a id="tocscategorydto"></a>

```json
{
  "EMPTY": {
    "EMPTY": {
      "EMPTY": {
        "EMPTY": {
          "EMPTY": null,
          "categoryId": null,
          "category": null,
          "rank": null,
          "status": null,
          "selected": null
        },
        "categoryId": 0,
        "category": "string",
        "rank": 0,
        "status": 0,
        "selected": true
      },
      "categoryId": 0,
      "category": "string",
      "rank": 0,
      "status": 0,
      "selected": true
    },
    "categoryId": 0,
    "category": "string",
    "rank": 0,
    "status": 0,
    "selected": true
  },
  "categoryId": 0,
  "category": "string",
  "rank": 0,
  "status": 0,
  "selected": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|EMPTY|[CategoryDTO](#schemacategorydto)|false|none||none|
|categoryId|integer|false|none||分类id|
|category|string|false|none||分类名称|
|rank|integer|false|none||分类级别|
|status|integer|false|none||0-未发布,1-已发布|
|selected|boolean|false|none||被选择|

<h2 id="tocS_NotifyMsgDTO">NotifyMsgDTO</h2>

<a id="schemanotifymsgdto"></a>
<a id="schema_NotifyMsgDTO"></a>
<a id="tocSnotifymsgdto"></a>
<a id="tocsnotifymsgdto"></a>

```json
{
  "msgId": 0,
  "relatedId": "string",
  "relatedInfo": "string",
  "operateUserId": 0,
  "operateUserName": "string",
  "operateUserPhoto": "string",
  "type": 0,
  "msg": "string",
  "state": 0,
  "createTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|msgId|integer|false|none||none|
|relatedId|string|false|none||消息关联的主体，如视频、评论|
|relatedInfo|string|false|none||关联信息|
|operateUserId|integer|false|none||发起消息的用户id|
|operateUserName|string|false|none||发起消息的用户名|
|operateUserPhoto|string|false|none||发起消息的用户头像|
|type|integer|false|none||消息类型|
|msg|string|false|none||消息正文|
|state|integer|false|none||1 已读/ 0 未读|
|createTime|string|false|none||消息产生时间|

<h2 id="tocS_VideoDoc">VideoDoc</h2>

<a id="schemavideodoc"></a>
<a id="schema_VideoDoc"></a>
<a id="tocSvideodoc"></a>
<a id="tocsvideodoc"></a>

```json
{
  "id": 0,
  "authorId": 0,
  "title": "string",
  "thumbnail": "string",
  "picture": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer|false|none||业务id|
|authorId|integer|false|none||作者id|
|title|string|false|none||标题|
|thumbnail|string|false|none||描述|
|picture|string|false|none||视频封面|
|status|integer|false|none||状态：0-未发布,1-已发布,2-待审核|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||修改时间|

<h2 id="tocS_SubCommentDTO1">SubCommentDTO1</h2>

<a id="schemasubcommentdto1"></a>
<a id="schema_SubCommentDTO1"></a>
<a id="tocSsubcommentdto1"></a>
<a id="tocssubcommentdto1"></a>

```json
{
  "parentContent": "string",
  "userId": 0,
  "userName": "string",
  "userPhoto": "string",
  "commentTime": 0,
  "commentContent": "string",
  "commentId": 0,
  "praiseCount": 0,
  "praised": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|parentContent|string|false|none||父评论内容|
|userId|integer|false|none||用户ID|
|userName|string|false|none||用户名|
|userPhoto|string|false|none||用户图像|
|commentTime|integer|false|none||评论时间|
|commentContent|string|false|none||评论内容|
|commentId|integer|false|none||评论id|
|praiseCount|integer|false|none||点赞数量|
|praised|boolean|false|none||true 表示当前用户点赞|

<h2 id="tocS_Status">Status</h2>

<a id="schemastatus"></a>
<a id="schema_Status"></a>
<a id="tocSstatus"></a>
<a id="tocsstatus"></a>

```json
{
  "code": 0,
  "msg": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|msg|string|false|none||none|

