package com.example.kaixuan.worryfreetutor.base;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import static com.example.kaixuan.worryfreetutor.login.AliyunSmsUtils.querySendDetails;
import static com.example.kaixuan.worryfreetutor.login.AliyunSmsUtils.sendSms;

public class MessageShort {
    static final String mtest= "message short +";

    public static void ms(final String phone, final String code){

        //此功能已经测试好，待整体完成再删除注释
         //发短信
         new Thread(new Runnable()
         {
         @Override
         public void run()
         {
         try
         {
         //发短信
         SendSmsResponse response = sendSms(phone,code);
         System.out.println("短信接口返回的数据----------------");
         System.out.println(mtest+"Code=" + response.getCode());

         System.out.println(mtest+"Message=" + response.getMessage());
         System.out.println(mtest+"RequestId=" + response.getRequestId());
         System.out.println(mtest+"BizId=" + response.getBizId());


         //查明细
         if(response.getCode() != null && response.getCode().equals("OK"))
         {
         QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
         System.out.println("短信明细查询接口返回数据----------------");
         System.out.println(mtest+"Code=" + querySendDetailsResponse.getCode());
         System.out.println(mtest+"Message=" + querySendDetailsResponse.getMessage());
         int i = 0;
         for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
         {
         System.out.println(mtest+"SmsSendDetailDTO["+i+"]:");
         System.out.println(mtest+"Content=" + smsSendDetailDTO.getContent());
         System.out.println(mtest+"ErrCode=" + smsSendDetailDTO.getErrCode());
         System.out.println(mtest+"OutId=" + smsSendDetailDTO.getOutId());
         System.out.println(mtest+"PhoneNum=" + smsSendDetailDTO.getPhoneNum());
         System.out.println(mtest+"ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
         System.out.println(mtest+"SendDate=" + smsSendDetailDTO.getSendDate());
         System.out.println(mtest+"SendStatus=" + smsSendDetailDTO.getSendStatus());
         System.out.println(mtest+"Template=" + smsSendDetailDTO.getTemplateCode());
         }
         System.out.println(mtest+"TotalCount=" + querySendDetailsResponse.getTotalCount());
         System.out.println(mtest+"RequestId=" + querySendDetailsResponse.getRequestId());
         }
         } catch (ClientException e)
         {
         e.printStackTrace();
         }
         //System.out.println(response.getData());
         System.out.println("结束");


         }
         }).start();

    }
}
