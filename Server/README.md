# Run the server

```
npm run server
```

# APIs

## Register

```
POST http://localhost:8080/api/auth/register
```

## Login

```
POST http://localhost:8080/api/auth/login
```

# Models

###### Users

    name: string
    username: string
    password: string
    email: string
    address: string
    about: string
    avatar: blob
    cookpadId: string
    userType: boolean

###### Login

    username: string
    password: string

###### Register

    name: string
    username: string
    password: string
    email: string
    userType: boolean

###### UserInfo

    name: string
    email: string
    address: string
    about: string
    avatar: blob
    cookpadId: string
