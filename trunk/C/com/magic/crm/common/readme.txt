这个目录主要收集了一些系统共通的类，类说明为：

WebAction: 继承了Struts的Action类，里面加入了数据库连接的取得和释放，继承它的所有系统Action
  类不必使用try-catch-finally语句，可以直接引用数据库连接conn，使用完成后无需手动进行commit、
  rollback和free操作。

WebForm: 继承了Struts的ActionForm类，加入了actionType的操作标志属性，用于区别页面触发的事件

Xjwang