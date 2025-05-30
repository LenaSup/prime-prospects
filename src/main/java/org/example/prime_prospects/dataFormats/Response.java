package org.example.prime_prospects.dataFormats;

import java.util.ArrayList;

public class Response {
    public Long id;
    public String position;
    public String outName;
    public boolean applicationDate;
    public boolean status;

    public Response(String id,
                    String position,
                    String outName,
                    String applicationDate,
                    String status){
        this.id = Long.parseLong(id);
        this.position = position;
        this.outName = outName;
        this.applicationDate = applicationDate.equals("true");
        this.status = status.equals("true");
    }

    public ArrayList<String> getStartInfo(){
        ArrayList<String> startInfo = new ArrayList<>();
        startInfo.add(position);
        startInfo.add(outName);
        return startInfo;
    }
}
