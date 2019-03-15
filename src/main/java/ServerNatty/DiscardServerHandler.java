package ServerNatty;

import DataBaseMain.MySQLComander;
import DataBaseMain.Units;
import DataBaseMain.UnitsDAO;

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
        UnitsDAO unitsDAO = new UnitsDAO();

        switch(command[0]){
            case ("add"):
                unitsDAO.AddToTable(new Units(command[1],command[2],Integer.parseInt(command[3])));
                break;
            case("GetNameById"):
                ctx.channel().writeAndFlush(unitsDAO.GetUnit(Integer.parseInt(command[1])).toString()+"\n");
                break;
            case("StringDB"):
                ctx.channel().writeAndFlush(unitsDAO.ShowTable().toString());
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
                Unpooled.buffer().writeBytes("Ok \n".getBytes())
        );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}