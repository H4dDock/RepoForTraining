package ServerNatty;

import DataBaseMain.MySQLComander;
import io.netty.buffer.ByteBuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.sql.SQLException;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws SQLException {

        String[] command = (msg.toString()).replace("\n"," ").split(" ");
        MySQLComander mySQLComander = MySQLComander.GetInstance();

        switch(command[0]){
            case ("add"):
                mySQLComander.InsertIntoUnitsDB(command[1],command[2],command[3],Integer.parseInt(command[4]));
                break;
            case("GetName"):
                ctx.channel().writeAndFlush(mySQLComander.GetArrOfNames(command[1]).toString());
                break;
            case("StringDB"):
                ctx.channel().writeAndFlush(mySQLComander.StringDB(command[1]));
                break;
            case("DeleteById"):
                mySQLComander.DeleteById(command[1],Integer.parseInt(command[2]));
                break;
            case("DeleteByName"):
                mySQLComander.DeleteById(command[1],command[2]);
                break;
            case("UpdateName"):
                mySQLComander.UpdateNameById(command[1],command[2],Integer.parseInt(command[3]));
                break;
            case("UpdateMoney"):
                mySQLComander.UpdateMoneyById(command[1],Integer.parseInt(command[2]),Integer.parseInt(command[3]));
                break;
            case("Help"):
                ctx.channel().writeAndFlush("add NameDB Name Email Money - for add new entry\n" +
                        "GetName NameDB - for get list of name\nStringDB NameDB - for print DB.");
                break;
            default:
                ctx.channel().writeAndFlush("Command not found.");
                break;
        }

        ctx.channel().writeAndFlush(
                Unpooled.buffer().writeBytes("Ok ".getBytes())
        );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}