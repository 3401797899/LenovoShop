from django.db import models

class User(models.Model):
    gender_choice = (
        (0, "保密"),
        (1, "男"),
        (2, "女")
    )
    email = models.EmailField(verbose_name="邮箱 / 账号", unique=True)
    nickname = models.CharField(verbose_name='昵称', max_length=255)
    avatar = models.CharField(max_length=255, verbose_name="头像")
    gender = models.IntegerField(choices=gender_choice, verbose_name="性别", default=0)
    password = models.CharField(max_length=100, verbose_name="密码", help_text="留空则不修改密码", blank=True)
    balance = models.IntegerField(verbose_name="余额", default=0)
    created_time = models.DateTimeField(auto_now_add=True, verbose_name="注册时间")

    class Meta:
        db_table = "users"

class Order(models.Model):
    order_status = (
        (1, "待付款"),
        (2, "待发货"),
        (3, "待收货"),
        (4, "已完成"),
    )
    order_id = models.CharField(max_length=100, verbose_name="订单ID")
    user = models.ForeignKey(to=User, on_delete=models.PROTECT, verbose_name="购买用户")
    payment = models.IntegerField(verbose_name="总金额")
    status = models.IntegerField(choices=order_status, verbose_name="订单状态")
    express_name = models.CharField(max_length=50, verbose_name="快递公司", null=True, blank=True)
    express_number = models.CharField(max_length=100, verbose_name="快递单号", null=True, blank=True)
    created_time = models.DateTimeField(auto_now_add=True, verbose_name="订单创建时间")
    payment_time = models.DateTimeField(verbose_name="支付时间", null=True, blank=True)
    consign_time = models.DateTimeField(verbose_name="发货时间", null=True, blank=True)
    end_time = models.DateTimeField(verbose_name="订单完成时间", null=True, blank=True)
    # 购买用户信息
    name = models.CharField(max_length=60, verbose_name="姓名")
    phone = models.CharField(max_length=30, verbose_name="电话", null=True)
    dz = models.TextField(verbose_name="详细地址", null=True, blank=True)
    # 购买的商品信息
    products = models.ManyToManyField(to='ProductCount', verbose_name="购买的商品")

    class Meta:
        db_table = "orders"
        ordering = ['-id']

class Category(models.Model):
    name = models.CharField(max_length=255, verbose_name="分类名称")

    class Meta:
        db_table = "categories"

class Config(models.Model):
    product = models.ForeignKey(to=Product, on_delete=models.PROTECT, verbose_name="产品")
    name = models.CharField(max_length=255, verbose_name="配置名称")
    brief = models.CharField(max_length=255, verbose_name="配置简介")
    price = models.IntegerField(verbose_name="价格")
    value = models.CharField(max_length=1024, verbose_name="配置值")

    class Meta:
        db_table = "configs"

class Product(models.Model):
    product_id = models.CharField(max_length=100, verbose_name="产品ID")
    name = models.CharField(max_length=255, verbose_name="产品名称")
    brief = models.CharField(max_length=255, verbose_name="产品简介")
    pic_url = models.CharField(verbose_name="产品图片", max_length=255,default='/media/logo.png')
    price = models.IntegerField(verbose_name="价格")
    category = models.ForeignKey(to=Category, on_delete=models.DO_NOTHING, verbose_name="产品分类")


    class Meta:
        db_table = "products"

class ProductCount(models.Model):
    product = models.ForeignKey(to=Product, on_delete=models.DO_NOTHING, verbose_name="产品")
    price = models.IntegerField(verbose_name="单价")
    count = models.IntegerField(verbose_name="数量")

    class Meta:
        db_table = "product_counts"
        verbose_name = "购买的产品和产品数量"

class Address(models.Model):
    user = models.ForeignKey(to=User, on_delete=models.PROTECT, verbose_name="用户")
    name = models.CharField(max_length=60, verbose_name="姓名")
    phone = models.CharField(max_length=30, verbose_name="电话", null=True)
    dz = models.TextField(verbose_name="详细地址", null=True, blank=True)

    class Meta:
        db_table = "addresses"
        ordering = ['-id']

