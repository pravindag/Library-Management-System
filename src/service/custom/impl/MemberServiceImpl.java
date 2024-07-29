package service.custom.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.custom.MemberDao;
import dto.MemberDto;
import entity.MemberEntity;
import service.custom.MemberService;

public class MemberServiceImpl implements MemberService{

    private MemberDao memberDao = (MemberDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.MEMBER);

    @Override
    public String save(MemberDto memberDto) throws Exception {
        MemberEntity entity = getMemberEntity(memberDto);
        return memberDao.save(entity);
    }

    @Override
    public String update(MemberDto memberDto) throws Exception {
        MemberEntity entity = getMemberEntity(memberDto);
        return memberDao.update(entity);
    }

    @Override
    public String delete(String memberId) throws Exception {
        return memberDao.delete(memberId);
    }

    @Override
    public MemberDto get(String memberId) throws Exception {
        MemberEntity entity = memberDao.get(memberId);
        return getMemberDto(entity);
    }

    @Override
    public ArrayList<MemberDto> getAll() throws Exception {
        ArrayList<MemberDto> memberDtos = new ArrayList<>();

        ArrayList<MemberEntity> memberEntitys = memberDao.getAll();

        for (MemberEntity entity : memberEntitys) {
            MemberDto dto = getMemberDto(entity);
            memberDtos.add(dto);
        }
        return memberDtos;
    }

    private MemberDto getMemberDto(MemberEntity entity){

        MemberDto memberEntityDto = new MemberDto(
                    entity.getMemberId(), entity.getFirstName(), entity.getLastName(),
                    entity.getDateOfBirth(), entity.getAddress(), entity.getPhoneNumber(), 
                    entity.getEmail());

        return memberEntityDto;

    }
    
    private MemberEntity getMemberEntity(MemberDto dto){

        MemberEntity entity = new MemberEntity(
                    dto.getMemberId(), dto.getFirstName(), dto.getLastName(),
                    dto.getDateOfBirth(), dto.getAddress(), dto.getPhoneNumber(), 
                    dto.getEmail());

         return entity;
    }

    @Override
    public MemberDto getLastEnteredId() throws Exception {
        MemberEntity entity = memberDao.getLastEnteredId();
        return getMemberDto(entity);
    }

}
