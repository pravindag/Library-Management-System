package service.custom;

import java.util.ArrayList;

import dto.MemberDto;
import service.SuperService;

public interface MemberService extends SuperService{

    public String save(MemberDto memberDto) throws Exception;
    public String update(MemberDto memberDto) throws Exception;
    public String delete(String memberId) throws Exception;
    public MemberDto get(String memberId) throws Exception;
    public ArrayList<MemberDto> getAll() throws Exception;
    public MemberDto getLastEnteredId() throws Exception;

}
