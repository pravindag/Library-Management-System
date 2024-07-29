package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.MemberDao;
import entity.MemberEntity;

public class MemberDaoImpl implements MemberDao{

    @Override
    public String save(MemberEntity entity) throws Exception {
        boolean isSaved = CrudUtil.executeUpdate("INSERT INTO table_member VALUES(?,?,?,?,?,?,?)",
                                    entity.getMemberId(), entity.getFirstName(), entity.getLastName(), entity.getDateOfBirth(), 
                                    entity.getAddress(), entity.getPhoneNumber(), entity.getEmail());
        return isSaved ? "Success" : "Fail";
    }

    @Override
    public String update(MemberEntity entity) throws Exception {
        boolean isUpdated = CrudUtil.executeUpdate("UPDATE table_member SET first_name=?, last_name=?, date_of_birth = ?, address=?, phone_number=?, email=? WHERE member_id =?",
        entity.getFirstName(), entity.getLastName(), entity.getDateOfBirth(), 
        entity.getAddress(), entity.getPhoneNumber(), entity.getEmail(), entity.getMemberId());
        return isUpdated ? "Success" : "Fail";
    }

    @Override
    public String delete(String memberId) throws Exception {
        boolean isDeleted = CrudUtil.executeUpdate("DELETE FROM table_member WHERE member_id =?", memberId);
        return isDeleted ? "Success" : "Fail";
    }

    @Override
    public MemberEntity get(String memberId) throws Exception {

        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_member WHERE member_id = ?", memberId);

        if(rst.next()){
            return new MemberEntity(
                    rst.getString("member_id"),
                    rst.getString("first_name"), 
                    rst.getString("last_name"), 
                    rst.getString("date_of_birth"), 
                    rst.getString("address"), 
                    rst.getString("phone_number"),
                    rst.getString("email"));
        }
        
        return null;
    }

    @Override
    public ArrayList<MemberEntity> getAll() throws Exception {
        ArrayList<MemberEntity> memberEntity = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_member");

        while (rst.next()) {
            MemberEntity entity = new MemberEntity(
                                rst.getString("member_id"),
                                rst.getString("first_name"), 
                                rst.getString("last_name"), 
                                rst.getString("date_of_birth"), 
                                rst.getString("address"), 
                                rst.getString("phone_number"),
                                rst.getString("email"));
            memberEntity.add(entity);
        }

        return memberEntity;
    }

    @Override
    public MemberEntity getLastEnteredId() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_member ORDER BY CAST(SUBSTRING(member_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(rst.next()){
            return new MemberEntity(
                    rst.getString("member_id"),
                    rst.getString("first_name"), 
                    rst.getString("last_name"), 
                    rst.getString("date_of_birth"), 
                    rst.getString("address"), 
                    rst.getString("phone_number"),
                    rst.getString("email"));
        }else{

            ResultSet all = CrudUtil.executeQuery("SELECT * FROM table_member");
            
            if(all.getRow() == 0){
                    return new MemberEntity("M000", "", "", "", "", "", "");
            }else{
                return new MemberEntity();
            }
        }
    }

}
