// 用户服务的Thrift IDL定义文件
namespace java com.ximalaya.ssd.thrift

// 用户信息结构体
struct User {
    1: i64 id,
    2: string name,
    3: string email,
    4: i32 age
}

// 用户服务异常
exception UserNotFoundException {
    1: string message
}

// 用户服务接口
service UserService {
    // 根据ID获取用户信息
    User getUserById(1: i64 userId) throws (1: UserNotFoundException ex),
    
    // 创建新用户
    i64 createUser(1: User user),
    
    // 更新用户信息
    bool updateUser(1: User user) throws (1: UserNotFoundException ex),
    
    // 删除用户
    bool deleteUser(1: i64 userId) throws (1: UserNotFoundException ex),
    
    // 获取所有用户列表
    list<User> getAllUsers(),
    
    // 简单的ping方法用于测试连接
    string ping()
} 