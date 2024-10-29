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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductionPlanCreateController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDepts = new DepartmentDBContext();
        
        req.setAttribute("products", dbProduct.list());
        req.setAttribute("depts", dbDepts.get("WS"));
        
        req.getRequestDispatcher("../view/productionplan/create.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        boolean hasErrors = false;
        
        // Lấy các tham số từ yêu cầu
        String name = req.getParameter("name");
        String fromDate = req.getParameter("from");
        String toDate = req.getParameter("to");

        // Kiểm tra các điều kiện hợp lệ và thêm lỗi vào request nếu có
        if (name == null || name.isEmpty()) {
            req.setAttribute("nameError", "Name has not been completed.");
            hasErrors = true;
        }
        if (fromDate == null || fromDate.isEmpty()) {
            req.setAttribute("fromError", "Start date has not been completed.");
            hasErrors = true;
        }
        if (toDate == null || toDate.isEmpty()) {
            req.setAttribute("toError", "End date has not been completed.");
            hasErrors = true;
        }

        Date start = null;
        Date end = null;
        if (fromDate != null && toDate != null && !fromDate.isEmpty() && !toDate.isEmpty()) {
            try {
                start = Date.valueOf(fromDate);
                end = Date.valueOf(toDate);
                Date currentDate = new Date(System.currentTimeMillis());

                // Kiểm tra ngày bắt đầu và ngày kết thúc không trước ngày hiện tại
                if (start.before(currentDate)) {
                    req.setAttribute("fromError", "Start date must not be before the current date.");
                    hasErrors = true;
                }
                if (end.before(currentDate)) {
                    req.setAttribute("toError", "End date must not be before the current date.");
                    hasErrors = true;
                }
                // Kiểm tra ngày bắt đầu phải trước ngày kết thúc
                if (!start.before(end)) {
                    req.setAttribute("dateError", "Start date must be earlier than end date.");
                    hasErrors = true;
                }
            } catch (IllegalArgumentException e) {
                req.setAttribute("dateError", "Invalid date format.");
                hasErrors = true;
            }
        }

        // Kiểm tra các sản phẩm
        String[] pids = req.getParameterValues("pid");
        boolean hasValidProduct = false;

        if (pids != null) {
            for (String pid : pids) {
                String raw_quantity = req.getParameter("quantity" + pid);
                String raw_cost = req.getParameter("cost" + pid);

                int quantity = (raw_quantity != null && !raw_quantity.isEmpty()) ? Integer.parseInt(raw_quantity) : 0;
                float cost = (raw_cost != null && !raw_cost.isEmpty()) ? Float.parseFloat(raw_cost) : 0;
                
                if (quantity > 0 && cost > 0) {
                    hasValidProduct = true;
                }
            }
        }
        
        if (!hasValidProduct) {
            req.setAttribute("productError", "At least one product must have valid quantity and cost.");
            hasErrors = true;
        }

        // Nếu có lỗi, trả về trang tạo kế hoạch cùng với các thông báo lỗi
        if (hasErrors) {
            doAuthorizedGet(req, resp, account);
            return;
        }

        // Nếu không có lỗi, tiến hành thêm kế hoạch
        Plan plan = new Plan();
        plan.setName(name);
        plan.setStart(start); // Sử dụng ngày bắt đầu đã phân tích
        plan.setEnd(end);     // Sử dụng ngày kết thúc đã phân tích
        Department d = new Department();
        d.setId(Integer.parseInt(req.getParameter("did")));
        plan.setDept(d);
        plan.setStatus("todo");

        for (String pid : pids) {
            PlanCampain c = new PlanCampain();
            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            c.setProduct(p);
            c.setPlan(plan);

            String raw_quantity = req.getParameter("quantity" + pid);
            String raw_cost = req.getParameter("cost" + pid);

            int quantity = (raw_quantity != null && !raw_quantity.isEmpty()) ? Integer.parseInt(raw_quantity) : 0;
            float cost = (raw_cost != null && !raw_cost.isEmpty()) ? Float.parseFloat(raw_cost) : 0;

            if (quantity > 0 && cost > 0) {
                c.setQuantity(quantity);
                c.setCost(cost);
                plan.getCampains().add(c);
            }
        }

        PlanDBContext db = new PlanDBContext();
        db.insert(plan);
        req.setAttribute("successMessage", "Your plan has been added successfully!");
        doAuthorizedGet(req, resp, account);
    }
}
