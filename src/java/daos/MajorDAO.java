/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.MajorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author ACER
 */
public class MajorDAO {
    public List<MajorDTO> listAll() throws SQLException, ClassNotFoundException{
        List<MajorDTO> list = null;
        System.out.println("list 1");
        Connection con = DBUtils.makeConnection();
        System.out.println("list 1.1");
        Statement stm = con.createStatement();
        System.out.println("list 1.2");
        ResultSet rs = stm.executeQuery("select * from [Major] ");
        System.out.println("list 2");
        list = new ArrayList();
        while (rs.next()) {
            MajorDTO major = new MajorDTO();
            major.setMajor_id(rs.getInt("major_id"));
            major.setMajor_name(rs.getString("major_name"));
            list.add(major);
        }
        
        System.out.println("list 3");
        con.close();
        return list;
    }
    public MajorDTO selectOne(String id) throws SQLException, ClassNotFoundException {
        MajorDTO major = new MajorDTO();
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("select * from [Major] where [major_id] = ?");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            major.setMajor_id(rs.getInt("major_id"));
            major.setMajor_name(rs.getString("major_name"));
        }
        con.close();
        return major;
    }
}