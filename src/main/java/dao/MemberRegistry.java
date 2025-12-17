package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entity.member.Member;
import exceptions.MemberNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemberRegistry {
    private File memberFile;
    private ObjectMapper mapper = new ObjectMapper();

    public MemberRegistry(String fileName) {
        this.memberFile = new File(fileName);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void removeMembersByID(List<String> ids) throws IOException, MemberNotFoundException {
        List<Member> members = getMembers();
        //Om det inte finns någon medlem med givet id så kan det inte tas bort, därav är det troligtvis fel någonstans
        for(String id : ids) {
            if (!members.removeIf(m -> m.getId().equals(id))) {
                throw new MemberNotFoundException("Medlem med id \"" + id + "\" finns inte i medlemsregister.");
            }
        }
        reloadFile(members);
    }

    public boolean addMember(Member member) throws IOException{
        List<Member> members = getMembers();
        members.add(member);
        reloadFile(members);
        return true;
    }

    /**
     * Uppdaterar medlemmen genom att i listan ta bort den medlem som är kopplad till medlemmens ID, och lägger sedan
     * till den uppdaterade medlemmen till listan som sedan skickas vidare för att uppdatera filen.
     * @param member den uppdaterade medlemmen
     * @throws IOException
     * @throws MemberNotFoundException
     */
    public void updateMember(Member member) throws IOException, MemberNotFoundException {
        List<Member> members = getMembers();
        //Om det inte finns någon medlem med givet id så kan det inte tas bort, därav är det troligtvis fel någonstans
        if (!members.removeIf(m -> m.getId().equals(member.getId()))) {
            throw new MemberNotFoundException("Medlem med id \"" + member.getId() + "\" finns inte i medlemsregister.");
        }
        members.add(member);
        reloadFile(members);
    }

    public List<Member> getMembers() throws IOException{
        if (!memberFile.exists() || memberFile.length() == 0) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(mapper.readValue(memberFile, Member[].class)));
    }

    /**
     * Skriver in hela listan med medlemmar till fil.
     * Om det inte går att skriva in filen på något sätt så ska man återgå till den tidigare listan av medlemmar.
     * @param members listan med medlemmar som ska skrivas in
     * @throws IOException om det inte går att skriva in.
     */
    public void reloadFile(List<Member> members) throws IOException{
        List<Member> oldList = getMembers();
        try{
            mapper.writeValue(memberFile, members);
        }catch (IOException e){
            System.out.println("Skriver in gamla listan");
            mapper.writeValue(memberFile, oldList);
            throw new IOException(e);
        }
    }

    public File getMemberFile() {
        return memberFile;
    }

    public void setMemberFile(File memberFile) {
        this.memberFile = memberFile;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
