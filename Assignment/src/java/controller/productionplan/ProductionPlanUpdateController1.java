package controller.productionplan;

import controller.accesscontrol.BaseRBACController;
import dal.DepartmentDBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import entity.Department;
import entity.Plan;
import entity.PlanCampain;
import entity.Product;
import entity.accesscontrol.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductionPlanUpdateController1 extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        try {
            // Lấy id từ request và kiểm tra nếu id hợp lệ
            String rawId = req.getParameter("id");
            if (rawId == null || rawId.isEmpty()) {
                resp.getWriter().println("Invalid Plan ID");
                return;
            }
            int id = Integer.parseInt(rawId);

            // Lấy các thông tin cần thiết để hiển thị trong form update
            ProductDBContext dbProduct = new ProductDBContext();
            DepartmentDBContext dbDepts = new DepartmentDBContext();
            PlanDBContext plans = new PlanDBContext();

            Plan plan = plans.get(id);
            if (plan == null) {
                resp.getWriter().println("Plan not found with ID: " + id);
                return;
            }

            // Tính danh sách ngày từ start đến end
            List<Date> dateList = getDateList(plan.getStart(), plan.getEnd());

            req.setAttribute("plan", plan);
            req.setAttribute("dateList", dateList);  // truyền danh sách ngày vào request
            req.setAttribute("products", dbProduct.list());
            req.setAttribute("depts", dbDepts.get("WS")); // Chỉnh lại thành danh sách department

            req.getRequestDispatcher("../view/productionplan/update.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.getWriter().println("Invalid Plan ID format.");
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String raw_id = req.getParameter("id");
        String raw_name = req.getParameter("name");
        String raw_from = req.getParameter("from");
        String raw_to = req.getParameter("to");
        String raw_did = req.getParameter("did");

        Plan plan = new Plan();
        plan.setId(Integer.parseInt(raw_id));
        plan.setName(raw_name);
        plan.setStart(Date.valueOf(raw_from));
        plan.setEnd(Date.valueOf(raw_to));
        
        Department dept = new Department();
        dept.setId(Integer.parseInt(raw_did));
        plan.setDept(dept);

        ArrayList<PlanCampain> campains = new ArrayList<>();
        for (String key : req.getParameterMap().keySet()) {
            if (key.startsWith("quantity")) {
                int productId = Integer.parseInt(key.replace("quantity", ""));
                int quantity = Integer.parseInt(req.getParameter(key));
                float cost = Float.parseFloat(req.getParameter("cost" + productId));
                int campainId = Integer.parseInt(req.getParameter("campainId" + productId));

                PlanCampain campain = new PlanCampain();
                campain.setId(campainId);
                campain.setQuantity(quantity);
                campain.setCost(cost);

                Product product = new Product();
                product.setId(productId);
                campain.setProduct(product);

                campains.add(campain);
            }
        }
        plan.setCampains(campains);

        PlanDBContext db = new PlanDBContext();
        db.update(plan);

        resp.getWriter().println("Update successful");
    }

    // Helper method to generate list of dates from start to end
    private List<Date> getDateList(Date start, Date end) {
        List<Date> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        
        while (!calendar.getTime().after(end)) {
            dateList.add(new Date(calendar.getTimeInMillis()));
            calendar.add(Calendar.DATE, 1);
        }
        
        return dateList;
    }
}
