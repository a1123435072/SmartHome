package com.njsyg.smarthomeapp.bll;

import android.os.Handler;
import android.util.Log;

import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyModuleCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_Task;
import com.njsyg.smarthomeapp.bll.socket_bll.models.View_TaskExecRecord_Info;

/**
 * Created by PCPC on 2016/6/2.
 */
public class missions_bll {

    private static final byte ModuleCode= MyModuleCode.MissionsModule;
    /// <summary>
    ///获取智能设备任务列表  通过智能设备sn，用户手机号
    /// </summary>
    public static void GetTaskListByPublicServer(T_Task t_task, Handler handler)
    {
        byte CommandCode = MyCommandCode.GetTaskListByPhoneNumAndDeviceSN;
        PacketDealTaskCommand(CommandCode, t_task, handler);
    }

    /**
     * 添加智能设备定时任务
     * @param t_task
     * @param handler
     */
    public static void AddTaskByPublicServer(T_Task t_task, Handler handler)
    {
        byte CommandCode = MyCommandCode.AddTask;
        PacketDealTaskCommand(CommandCode, t_task, handler);
    }

    /**
     * 修改智能设备定时任务
     * @param t_task
     * @param handler
     */
    public static void UpdTaskByPublicServer(T_Task t_task, Handler handler)
    {
        byte CommandCode = MyCommandCode.UpdTask;
        PacketDealTaskCommand(CommandCode, t_task, handler);
    }

    /**
     * 删除智能设备定时任务
     * @param t_task
     * @param handler
     */
    public static void DelTaskByPublicServer(T_Task t_task, Handler handler)
    {
        byte CommandCode = MyCommandCode.DeleteTask;
        PacketDealTaskCommand(CommandCode, t_task, handler);
    }

    /**
     * 修改智能设备定时任务状态
     * @param t_task
     * @param handler
     */
    public static void UpdTaskStateByPublicServer(T_Task t_task, Handler handler)
    {
        byte CommandCode = MyCommandCode.UpdTaskState;
        PacketDealTaskCommand(CommandCode, t_task, handler);
    }

    /**
     * 获取任务历史执行情况
     * @param view_taskExecRecord_info
     * @param handler
     */
    public static void  GetTaskHisListByPublicServer(View_TaskExecRecord_Info view_taskExecRecord_info, Handler handler)
    {
        byte CommandCode = MyCommandCode.GetTaskHisList;
        PacketDealTaskHisCommand(CommandCode,view_taskExecRecord_info,handler);
    }
    /// <summary>
    ///打包命令 并向公网服务器发送消息
    /// </summary>
    private static void PacketDealTaskCommand(byte CommandCode,T_Task task,Handler handler)
    {
        //将t_device装成json
        String jsonStr =command_bll.SerializeObj(task);
        byte[] data = jsonStr.getBytes();
        byte[] sendMsg=command_bll.PacketCommand(ModuleCode,CommandCode,data);
        command_bll.SendDataToPublicServer(sendMsg,handler);
    }
    /// <summary>
    ///打包命令 并向公网服务器发送消息
    /// </summary>
    private static void PacketDealTaskHisCommand(byte CommandCode,View_TaskExecRecord_Info view_taskExecRecord_info,Handler handler)
    {
        //将t_device装成json
        String jsonStr =command_bll.SerializeObj(view_taskExecRecord_info);
        byte[] data = jsonStr.getBytes();
        byte[] sendMsg=command_bll.PacketCommand(ModuleCode,CommandCode,data);
        command_bll.SendDataToPublicServer(sendMsg,handler);
    }
}
