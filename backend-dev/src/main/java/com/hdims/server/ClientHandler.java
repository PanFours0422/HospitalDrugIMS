package com.hdims.server;

import com.hdims.service.AdminService;
import com.hdims.service.DoctorService;
import com.hdims.service.LoginService;
import com.hdims.service.NurseService;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final AdminService adminService;
    private final DoctorService doctorService;
    private final NurseService nurseService;
    private final LoginService loginService;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.adminService = new AdminService();
        this.doctorService = new DoctorService();
        this.nurseService = new NurseService();
        this.loginService = new LoginService();
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String requestJson;
            while ((requestJson = in.readLine()) != null) {
                Request request = Protocol.fromJson(requestJson, Request.class);
                Response response = handleRequest(request);
                String responseJson = Protocol.toJson(response);
                out.println(responseJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request) {
        Response response = new Response();
        try {
            switch (request.getAction()) {
                case "login":
                    response = handleLogin(request);
                    break;
                case "getAllDrugs":
                    response = handleGetAllDrugs(request);
                    break;
                case "addInventoryDrug":
                    response = handleAddInventoryDrug(request);
                    break;
                case "disposeExpiredDrugs":
                    response = handleDisposeExpiredDrugs(request);
                    break;
                case "addSupplier":
                    response = handleAddSupplier(request);
                    break;
                case "updateSupplier":
                    response = handleUpdateSupplier(request);
                    break;
                case "deleteSupplier":
                    response = handleDeleteSupplier(request);
                    break;
                case "getDoctorPrescriptions":
                    response = handleGetDoctorPrescriptions(request);
                    break;
                case "addPrescription":
                    response = handleAddPrescription(request);
                    break;
                case "updatePrescription":
                    response = handleUpdatePrescription(request);
                    break;
                case "deletePrescription":
                    response = handleDeletePrescription(request);
                    break;
                case "getAllHandledPrescriptions":
                    response = handleGetAllHandledPrescriptions(request);
                    break;
                case "handlePrescription":
                    response = handleHandlePrescription(request);
                    break;
                default:
                    response.setStatus("error");
                    response.setMessage("Unknown action: " + request.getAction());
            }
        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    private Response handleLogin(Request request) {
        boolean success = false;
        switch (request.getRole()) {
            case "Admin":
                success = loginService.adminLogin(request.getId(), request.getPassword());
                break;
            case "Doctor":
                success = loginService.doctorLogin(request.getId(), request.getPassword());
                break;
            case "Nurse":
                success = loginService.nurseLogin(request.getId(), request.getPassword());
                break;
        }
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Login successful");
        } else {
            response.setStatus("error");
            response.setMessage("Invalid credentials");
        }
        return response;
    }

    private Response handleGetAllDrugs(Request request) {
        Response response = new Response();
        switch (request.getRole()) {
            case "Admin":
            case "Doctor":
            case "Nurse":
                response.setStatus("success");
                response.setData(adminService.viewInventory());
                break;
            default:
                response.setStatus("error");
                response.setMessage("Invalid role: " + request.getRole());
        }
        return response;
    }

    private Response handleAddInventoryDrug(Request request) {
        boolean success = adminService.addInventoryDrug((String) request.getData(), (String) request.getData(), (Integer) request.getData(), (String) request.getData(), request.getId());
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Drug added to inventory");
        } else {
            response.setStatus("error");
            response.setMessage("Failed to add drug to inventory");
        }
        return response;
    }

    private Response handleDisposeExpiredDrugs(Request request) {
        boolean success = adminService.destroyExpiredDrugBatch((String) request.getData(), (String) request.getData(), request.getId());
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Expired drugs disposed");
        } else {
            response.setStatus("error");
            response.setMessage("Failed to dispose expired drugs");
        }
        return response;
    }

    private Response handleAddSupplier(Request request) {
        boolean success = adminService.addSupplier((String) request.getData(), (String) request.getData(), (String) request.getData(), (String) request.getData());
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Supplier added");
        } else {
            response.setStatus("error");
            response.setMessage("Failed to add supplier");
        }
        return response;
    }

    private Response handleUpdateSupplier(Request request) {
        boolean success = adminService.updateSupplier((String) request.getData(), (String) request.getData(), (String) request.getData(), (String) request.getData());
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Supplier updated");
        } else {
            response.setStatus("error");
            response.setMessage("Failed to update supplier");
        }
        return response;
    }

    private Response handleDeleteSupplier(Request request) {
        boolean success = adminService.deleteSupplier((String) request.getData());
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Supplier deleted");
        } else {
            response.setStatus("error");
            response.setMessage("Failed to delete supplier");
        }
        return response;
    }

    private Response handleGetDoctorPrescriptions(Request request) {
        Response response = new Response();
        response.setStatus("success");
        response.setData(doctorService.viewPrescriptions(request.getId()));
        return response;
    }

    private Response handleAddPrescription(Request request) {
        boolean success = doctorService.createPrescription(request.getId(), (String) request.getData(), (List<String>) request.getData(), (List<Integer>) request.getData());
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Prescription added");
        } else {
            response.setStatus("error");
            response.setMessage("Failed to add prescription");
        }
        return response;
    }

    private Response handleUpdatePrescription(Request request) {
        boolean success = false;
        if (request.getData() instanceof Map) {
            Map<String, Object> data = (Map<String, Object>) request.getData();
            if (data.containsKey("oldPdname") && data.containsKey("newPdname")) {
                success = doctorService.updatePrescriptionDrugName((Integer) data.get("pno"), (String) data.get("oldPdname"), (String) data.get("newPdname"));
            } else if (data.containsKey("pdname") && data.containsKey("newPdnum")) {
                success = doctorService.updatePrescriptionDrugQuantity((Integer) data.get("pno"), (String) data.get("pdname"), (Integer) data.get("newPdnum"));
            }
        }
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Prescription updated");
        } else {
            response.setStatus("error");
            response.setMessage("Failed to update prescription");
        }
        return response;
    }

    private Response handleDeletePrescription(Request request) {
        boolean success = doctorService.deletePrescription((Integer) request.getData());
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Prescription deleted");
        } else {
            response.setStatus("error");
            response.setMessage("Failed to delete prescription");
        }
        return response;
    }

    private Response handleGetAllHandledPrescriptions(Request request) {
        Response response = new Response();
        response.setStatus("success");
        response.setData(nurseService.viewHandledPrescriptions(request.getId()));
        return response;
    }

    private Response handleHandlePrescription(Request request) {
        boolean success = nurseService.handlePrescription((Integer) request.getData(), request.getId());
        Response response = new Response();
        if (success) {
            response.setStatus("success");
            response.setMessage("Prescription handled");
        } else {
            response.setStatus("error");
            response.setMessage("Failed to handle prescription");
        }
        return response;
    }
}
