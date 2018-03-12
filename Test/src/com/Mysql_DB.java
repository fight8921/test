package com;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 链接数据库测试mysql,ok表示成功连接
 * 
 * @author Administrator
 */
public class Mysql_DB
{
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private String driver = "com.alibaba.druid.pool.DruidDataSource";
    // private String url = "jdbc:mysql://127.0.0.1:3306/yinshu3";
    // 乱码转译
    // jdbc.url=jdbc\:mysql\://127.0.0.1\:3306/htxd?useUnicode\=true&characterEncoding\=utf8&zeroDateTimeBehavior\=convertToNull
    // private String url = "jdbc:mysql://218.242.45.26:3306/htxd";
    private String url = "jdbc:mysql://106.14.164.251:3306/pwd";
    private String name = "tank";
    private String pwd = "a1234567";

    private void open()
    {
        if (con == null)
        {
            try
            {
                Class.forName(driver);
                con = DriverManager.getConnection(url, name, pwd);
                System.out.println("ok");
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /*
     * private void open(){ try { Context ctx=new InitialContext(); DataSource
     * ds=(DataSource)ctx.lookup("java:comp/env/jdbc/news");
     * con=ds.getConnection(); System.out.println("JNDI DB OK"); } catch
     * (NamingException e) { e.printStackTrace(); } catch (SQLException e) {
     * e.printStackTrace(); } }
     */
    public ResultSet query(String sql)
    {
        return query(sql, null);
    }

    public ResultSet query(String sql, Object[] param)
    {
        open();
        try
        {
            ps = con.prepareStatement(sql);
            if (param != null)
            {
                for (int i = 0; i < param.length; i++)
                {
                    ps.setObject(i + 1, param[i]);
                }
            }
            rs = ps.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rs;
    }

    public int update(String sql)
    {
        return update(sql, null);
    }

    public int update(String sql, Object[] param)
    {
        open();
        int num = 0;
        try
        {
            ps = con.prepareStatement(sql);
            if (param != null)
            {
                for (int i = 0; i < param.length; i++)
                {
                    ps.setObject(i + 1, param[i]);

                }
            }
            num = ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            close();
        }
        return num;
    }

    protected void close()
    {
        try
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null && !con.isClosed())
            {
                con.close();
                con = null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Mysql_DB db = new Mysql_DB();
        db.open();
        // getUserList();

    }

    public static void getUserList()
    {
        Mysql_DB db = new Mysql_DB();

        String sql = "SELECT * FROM `sys_users`";
        ResultSet resultSet = db.query(sql);
        try
        {
            while (resultSet.next())
            {
                Integer userKy = resultSet.getInt("user_ky");
                String loginId = resultSet.getString("login_id");
                String password = resultSet.getString("password");
                System.out.println("userKy=" + userKy + "     loginId=" + loginId + "     password=" + password);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.close();
        }

    }

    public List<SysUsers> getUserList1()
    {
        Mysql_DB db = new Mysql_DB();

        String sql = "SELECT * FROM `sys_users`";
        ResultSet resultSet = db.query(sql);
        List<SysUsers> userList = new ArrayList<SysUsers>();
        try
        {
            while (resultSet.next())
            {
                SysUsers user = new SysUsers(resultSet.getInt("userKy"), resultSet.getString("loginId"), resultSet.getString("password"));
                userList.add(user);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.close();
        }

        return userList;
    }

}



class SysUsers
{
    private Integer userKy;
    private String loginId;
    private String password;

    public Integer getUserKy()
    {
        return userKy;
    }
    public void setUserKy(Integer userKy)
    {
        this.userKy = userKy;
    }
    public String getLoginId()
    {
        return loginId;
    }
    public void setLoginId(String loginId)
    {
        this.loginId = loginId;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public SysUsers()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    public SysUsers(Integer userKy, String loginId, String password)
    {
        super();
        this.userKy = userKy;
        this.loginId = loginId;
        this.password = password;
    }

}
