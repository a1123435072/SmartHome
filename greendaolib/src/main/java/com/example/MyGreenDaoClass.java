package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGreenDaoClass {
    public static void main(String[] args) throws Exception {
        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(1, "njsyg.greendao.db.model");
//      当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
//      Schema schema = new Schema(1, "njsyg.bean");
        schema.setDefaultJavaPackageDao("njsyg.greendao.db.dao");

        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        // schema2.enableActiveEntitiesByDefault();
        schema.enableKeepSectionsByDefault();

        // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
        addNote(schema);

        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        new DaoGenerator().generateAll(schema, "../20161020/app/src/main/java");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {

        Entity t_device = schema.addEntity("DB_Device");
        t_device.addLongProperty("device_id").primaryKey();
        t_device.addStringProperty("device_sn");
        t_device.addStringProperty("hserver_sn");
        t_device.addStringProperty("device_ip");
        t_device.addIntProperty("device_type");
        t_device.addStringProperty("device_installPlace");
        t_device.addStringProperty("device_nickName");
        t_device.addStringProperty("remark");

        Entity t_homeserver = schema.addEntity("DB_HomeServer");
        t_homeserver.addLongProperty("hserver_id").primaryKey();
        t_homeserver.addStringProperty("hserver_sn");
        t_homeserver.addStringProperty("hserver_ip");
        t_homeserver.addStringProperty("hserver_nickName");
        t_homeserver.addStringProperty("remark");

        Entity t_hServerkey= schema.addEntity("DB_HServerKey");
        t_hServerkey.addLongProperty("id").primaryKey();
        t_hServerkey.addStringProperty("hserver_sn");
        t_hServerkey.addIntProperty("keytype");
        t_hServerkey.addStringProperty("keyvalue");
        t_hServerkey.addStringProperty("remark");

        Entity t_HServerAndUser=schema.addEntity("DB_HServerAndUser");
        t_HServerAndUser.addLongProperty("id").primaryKey();
        t_HServerAndUser.addStringProperty("hserver_sn");
        t_HServerAndUser.addStringProperty("user_phoneNumber");
        t_HServerAndUser.addStringProperty("hserver_nickName");
        t_HServerAndUser.addDateProperty("addtime");
        t_HServerAndUser.addStringProperty("remark");

        Entity t_user=schema.addEntity("DB_User");
        t_user.addLongProperty("user_id").primaryKey();
        t_user.addStringProperty("user_phoneNumber");
        t_user.addStringProperty("user_nickName");
        t_user.addStringProperty("user_pwd");
        t_user.addStringProperty("user_email");
        t_user.addStringProperty("user_headImg");
        t_user.addStringProperty("remark");

        Entity t_HServerOnlineRecord=schema.addEntity("DB_HServerOnlineRecord");
        t_HServerOnlineRecord.addLongProperty("id").primaryKey();
        t_HServerOnlineRecord.addStringProperty("hserver_sn");
        t_HServerOnlineRecord.addIntProperty("hserver_onlineState");
        t_HServerOnlineRecord.addDateProperty("updatetime");
        t_HServerOnlineRecord.addStringProperty("remark");

        Entity t_DeviceOnlineRecord=schema.addEntity("DB_DeviceOnlineRecord");
        t_DeviceOnlineRecord.addLongProperty("id").primaryKey();
        t_DeviceOnlineRecord.addStringProperty("device_sn");
        t_DeviceOnlineRecord.addIntProperty("device_onlineState");
        t_DeviceOnlineRecord.addDateProperty("updatetime");
        t_DeviceOnlineRecord.addStringProperty("remark");

        Entity t_DeviceRealData=schema.addEntity("DB_DeviceRealData");
        t_DeviceRealData.addLongProperty("id").primaryKey();
        t_DeviceRealData.addStringProperty("device_sn");
        t_DeviceRealData.addIntProperty("device_state");
        t_DeviceRealData.addFloatProperty("device_U");
        t_DeviceRealData.addFloatProperty("device_I");
        t_DeviceRealData.addFloatProperty("device_P");
        t_DeviceRealData.addFloatProperty("device_electricity");
        t_DeviceRealData.addDateProperty("updatetime");
        t_DeviceRealData.addStringProperty("remark");

        Entity t_DB_DeviceHisData=schema.addEntity("DB_DeviceHisData");
        t_DB_DeviceHisData.addLongProperty("id").primaryKey();
        t_DB_DeviceHisData.addStringProperty("device_sn");
        t_DB_DeviceHisData.addIntProperty("device_state");
        t_DB_DeviceHisData.addFloatProperty("device_U");
        t_DB_DeviceHisData.addFloatProperty("device_I");
        t_DB_DeviceHisData.addFloatProperty("device_P");
        t_DB_DeviceHisData.addFloatProperty("device_electricity");
        t_DB_DeviceHisData.addDateProperty("updatetime");
        t_DB_DeviceHisData.addStringProperty("remark");
    }
}
