package org.example.prime_prospects_api.ResponseElements;

import org.example.prime_prospects_api.essence.Response;
import org.example.prime_prospects_api.essence.parsers.ResponsePars;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    private ResponsePars responsePars;

    public void set(long id, boolean pick){
        Response response = responsePars.findById(id).get();
        response.setAccept(pick);
        response.setStatus(true);
        responsePars.save(response);
    }

    public void sive(Response response){
        responsePars.save(response);
    }

    public List<Response> getIn(long in_id){
        return responsePars.findAllByIn(in_id).get();
    }

    public List<Response> getOut(long out_id){
        return responsePars.findAllByOut(out_id).get();
    }
}
