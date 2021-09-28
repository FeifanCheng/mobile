# mobile

数据库需要本地建好schema “DB_I_T_PUSH”  
在hibernate.cfg.xml里改用户名和密码  

接口 (需设置Content-Type为application/json) 
1. http://localhost:8080/api/account/register  
参数：{  
    "code": 1,  
    "message": "Success",  
    "time": "2021-09-28T18:49:42.746",  
    "response_result": {   
        "userIdentity": {  
            "id": "7c113d70-639b-477d-a9c0-726847e6c0ce",  
            "name": "xiaohong",  
            "portrait": null,  
            "phone": "1598888",  
            "description": null,  
            "sex": 0,  
            "updateAt": "2021-09-28T18:49:42.735",  
            "follows": 0,  
            "followings": 0,  
            "isFollow": false  
        },  
        "token":   "MzI0M2VjMWYtNzg3Zi00MGRlLTk4NWYtYzEwMzBhODljYzkw",  
        "isBindService": false  
    }  
}    



