# Fleet 开发平台

当前最新版本： `1.0.0`（发布日期：20210422）

## 后端技术架构

- 基础框架：`Spring Boot 2.3.5.RELEASE`
- 持久层框架：`Mybatis-plus 3.4.1`
- 安全框架：`Apache Shiro 1.7.0`，`Jwt 3.11.0`
- 数据库连接池：`Alibaba Druid 1.1.22`
- 缓存框架：`Redis`
- 日志打印：`logback`

- 其他：`fastjson`，`POI`，`Swagger`，`quartz`，`activiti`，`lombok`（简化代码）等。

## 开发环境

- 语言：`Java 8`
- IDE(Java)： `Eclipse`安装`lombok`插件 或者 `IDEA`
- 依赖管理：`Maven`
- 数据库：`MySQL5.7+`  &  `Oracle 11g`
- 缓存：`Redis`

## 技术文档

- 在线演示：  
- 在线文档：  
- 常见问题：  

## 专项文档

#### 一、查询过滤器用法

```java
QueryWrapper<?> queryWrapper = QueryGenerator.initQueryWrapper(?, req.getParameterMap());
```

代码示例：

```java
@GetMapping(value = "/list")
public Result<IPage<JeecgDemo>> list(
    JeecgDemo fleetDemo,
    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, 
    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
    HttpServletRequest req) {
    Result<IPage<JeecgDemo>> result = new Result<IPage<JeecgDemo>>();

    //调用QueryGenerator的initQueryWrapper
    QueryWrapper<JeecgDemo> queryWrapper = QueryGenerator.initQueryWrapper(fleetDemo, req.getParameterMap());

    Page<JeecgDemo> page = new Page<JeecgDemo>(pageNo, pageSize);
    IPage<JeecgDemo> pageList = fleetDemoService.page(page, queryWrapper);
    result.setSuccess(true);
    result.setResult(pageList);
    return result;
}
```

- 查询规则 (本规则不适用于高级查询,高级查询有自己对应的查询类型可以选择 )

| 查询模式           | 用法    | 说明                         |
|---------- |-------------------------------------------------------|------------------|
| 模糊查询     | 支持左右模糊和全模糊  需要在查询输入框内前或后带\*或是前后全部带\* |    |
| 取非查询     | 在查询输入框前面输入! 则查询该字段不等于输入值的数据(数值类型不支持此种查询,可以将数值字段定义为字符串类型的) |    |
| \>  \>= < <=     | 同取非查询 在输入框前面输入对应特殊字符即表示走对应规则查询 |    |
| in查询     | 若传入的数据带,(逗号) 则表示该查询为in查询 |    |
| 多选字段模糊查询     | 上述4 有一个特例，若某一查询字段前后都带逗号 则会将其视为走这种查询方式 ,该查询方式是将查询条件以逗号分割再遍历数组 将每个元素作like查询 用or拼接,例如 现在name传入值 ,a,b,c, 那么结果sql就是 name like '%a%' or name like '%b%' or name like '%c%' |    |

#### 三、代码生成器

> 功能说明：   一键生成的代码（包括：`controller`、`service`、`dao`、`mapper`、`entity`、`vue`）
 
 - 模板位置： `src/main/resources/fleet/code-template`
 - 技术文档： 

#### 四、编码排重使用示例

重复校验效果：

1. 引入排重接口,代码如下:  
 
```javascript
import { duplicateCheck } from '@/api/api';
```

2. 找到编码必填校验规则的前端代码,代码如下:  
  
```html
<a-input placeholder="请输入编码" v-decorator="['code', validatorRules.code]"/>

code: {
    rules: [
        { required: true, message: '请输入编码!' },
        { validator: this.validateCode }
    ]
},
```

3. 找到`rules`里`validator`对应的方法在哪里,然后使用第一步中引入的排重校验接口.  
  以用户online表单编码为示例,其中四个必传的参数有:  
    
```javascript
{tableName: 表名, fieldName: 字段名, fieldVal: 字段值, dataId: 表的主键},
```

具体使用代码如下:

```javascript
validateCode(rule, value, callback) {
    let pattern = /^[a-z|A-Z][a-z|A-Z|\d|_|-]{0,}$/;
    if(!pattern.test(value)) {
        callback('编码必须以字母开头，可包含数字、下划线、横杠');
    } else {
        var params = {
            tableName: "onl_cgreport_head",
            fieldName: "code",
            fieldVal: value,
            dataId: this.model.id
        };
        duplicateCheck(params).then((res) => {
            if(res.success) {
                callback();
            } else {
                callback(res.message);
            }
        });
    }
},
```

## docker镜像用法

注意： 如果本地安装了`mysql`和`redis`,启动容器前先停掉本地服务，不然会端口冲突。
```bash
# windows
net stop redis
net stop mysql
# unix
systemctl stop redis
systemctl stop mysql
```
1. 修改项目配置文件`application.yml`
```yml
active: docker
```

2. 进入`Java`项目根路径，`maven`打包
```bash
mvn clean package
```

3. 构建镜像__容器组（当你改变本地代码，也可重新构建镜像）
```bash
docker-compose build
```

4. 配置host
```conf
# fleetboot
127.0.0.1   fleet-boot-redis
127.0.0.1   fleet-boot-mysql
127.0.0.1   fleet-boot-system
```

5. 启动镜像__容器组（也可取代运行中的镜像）
```bash
docker-compose up -d
```

6. 访问后台项目（注意要开启`swagger`）：[`http://localhost:8080/fleet-boot/doc.html`](http://localhost:8080/fleet-boot/doc.html)

## 鸣谢

本项目使用了以下第三方库/框架进行开发，按许可证种类进行分类

### MIT

* JWT
* lombok

### BSD

* Redis

### Apache-2.0

* Spring boot
* MyBatis-Plus
* Apache Shiro
* Alibaba Druid
* fastjson
* POI
* Swagger
* quartz
* activiti

### 其他

|Project|License|
|---|---|
|logback|EPL v1.0 and LGPL 2.1|
