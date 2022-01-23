# Chạy server

```
npm run server
```

# Danh sách APIs

## Đăng ký

```
http://localhost:8080/api/user/register
```

## Đăng nhập

```
http://localhost:8080/api/user/login
```

## Lấy thông tin người dùng

```
http://localhost:8080/api/user/get/:userId
```

## Chỉnh sửa thông tin người dùng

```
http://localhost:8080/api/user/edit/:userId
```

## Lấy toàn bộ recipes

```
http://localhost:8080/api/recipe/getAllRecipes
```

## Lấy recipe theo userId

```
http://localhost:8080/api/recipe/getRecipe/:userId
```

## Tạo recipe

```
http://localhost:8080/api/recipe/createRecipe/:userId
```

## Cập nhật số lượng likes của recipe

```
http://localhost:8080/api/recipe/editRecipeLike/:recipeId
```

## Cập nhật số lượng claps của recipe

```
http://localhost:8080/api/recipe/editRecipeClap/:recipeId
```

## Cập nhật số lượng heart của recipe

```
http://localhost:8080/api/recipe/editRecipeHeart/:recipeId
```

## Lấy tất cả category

```
GET http://localhost:8080/api/recipe/getAllCategories
```

## Tìm kiếm theo title

```
POST http://localhost:8080/api/recipe/search
Content-Type: application/json

{
    "payload": "string"
}
```

## Cập nhật số lượng likes của recipe

```
POST http://localhost:8080/api/recipe/editRecipeLike/${recipe_id}?like_num=${like_num}
```

## Cập nhật số lượng claps của recipe

```
POST http://localhost:8080/api/recipe/editRecipeClap/${recipe_id}?clap_num=${clap_num}
```

## Cập nhật số lượng hearts của recipe

```
POST http://localhost:8080/api/recipe/editRecipeHeart/${recipe_id}?heart_num=${heart_num}
```

## Cập nhật giỏ hàng theo mã người dùng

```
PUT http://localhost:8080/api/recipe/updateCart?userId=${userId}
Content-Type: application/json

{
    "recipeLst": ["61e3b5a285374ee7d655d12f"]
}
```

## Lấy thông tin giỏ hàng theo max nguoi dung

```
GET http://localhost:8080/api/recipe/getCart?userId=${userId}

```

# Models

## Đăng ký

```
name: string
username: string
password: string
role: number (1: nguời dùng, 2: shipper)
```

## Đăng nhập

```
username: string
password: string
```

## Lấy thông tin người dùng

```
_id: string
avatar: string
name: string
address: string
about: string
cookpadId: string
role: number
```

## Chỉnh sửa thông tin người dùng

```
avatar: string
name: string
address: string
about: string
cookpadId: string
role: number
```

## Lấy toàn bộ recipes

```
_id: string
author: []
title: string
description: string
origin: string
serves: number
cookTime: number
dateCreate: date
category: string
ingredients: [{
    name: string
}]
steps: [{
    name: string,
    picture: string
}]
likes: number
claps: number
hearts: number
```

## Cập nhật số lượng likes của recipe

```
hearts: string
```

## Cập nhật số lượng claps của recipe

```
claps: string
```

## Cập nhật số lượng hearts của recipe

```
hearts: string
```
