package org.acme.ehealth;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.smallrye.mutiny.Uni;
import org.acme.ehealth.dto.Patient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.panache.common.Sort;


@Path("ehealth")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class EHealthResource {
    private static final Logger LOGGER = Logger.getLogger(EHealthResource.class.getName());

    @GET
    public List<Patient> getListofPatient(){
        return (List<Patient>) Patient.listAll(Sort.by("patientname"));
    }
    @GET
    @Path("{id}")
    public Patient getSinglePatent(@PathParam Long id) {
        Patient entity = (Patient) Patient.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Patient with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Patient patient) {
        if (patient.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        patient.persist();
        return Response.ok(patient).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Patient updatePatient(@PathParam Long id, Patient patient) {
        if (patient.patientName == null) {
            throw new WebApplicationException("Patient Name was not set on request.", 422);
        }

        Patient entityPatient = (Patient) Patient.findById(id);

        if (entityPatient == null) {
            throw new WebApplicationException("Patient with id of " + id + " does not exist.", 404);
        }

        entityPatient.patientName = patient.patientName;
        entityPatient.patientAge = patient.patientAge;
        entityPatient.patientTel = patient.patientTel;
        entityPatient.patientAddress= patient.patientAddress;


        return entityPatient;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        Patient patiententity = (Patient) Patient.findById(id);
        if (patiententity == null) {
            throw new WebApplicationException("Patient with id of " + id + " does not exist.", 404);
        }
        patiententity.delete();
        return Response.status(204).build();
    }

}
