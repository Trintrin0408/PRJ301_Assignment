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

/**
 *
 * @author sonnt-local hand-some
 */
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

            req.setAttribute("plan", plan);
            req.setAttribute("products", dbProduct.list());
            req.setAttribute("depts", dbDepts.get("WS")); // Chỉnh lại thành danh sách department

            req.getRequestDispatcher("../view/productionplan/update.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.getWriter().println("Invalid Plan ID format.");
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        try {
            Plan plan = new Plan();
            plan.setId(Integer.parseInt(req.getParameter("id"))); // Gán lại ID của plan để cập nhật
            plan.setName(req.getParameter("name"));
            plan.setStart(Date.valueOf(req.getParameter("from")));
            plan.setEnd(Date.valueOf(req.getParameter("to")));

            // Thiết lập thông tin của Department
            Department d = new Department();
            d.setId(Integer.parseInt(req.getParameter("did")));
            plan.setDept(d);

            // Lấy danh sách sản phẩm từ request
            String[] pids = req.getParameterValues("pid");
            if (pids != null) {
                for (String pid : pids) {
                    PlanCampain c = new PlanCampain();

                    Product p = new Product();
                    p.setId(Integer.parseInt(pid));
                    c.setProduct(p);
                    c.setPlan(plan);

                    // Lấy số lượng và chi phí từ request
                    String raw_quantity = req.getParameter("quantity" + pid);
                    String raw_cost = req.getParameter("cost" + pid);

                    c.setQuantity(raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0);
                    c.setCost(raw_cost != null && raw_cost.length() > 0 ? Float.parseFloat(raw_cost) : 0);

                    // Chỉ thêm vào danh sách khi số lượng và chi phí lớn hơn 0
                    if (c.getQuantity() > 0 && c.getCost() > 0) {
                        plan.getCampains().add(c);
                    }
                }
            }

            // Kiểm tra nếu plan có chiến dịch (campains) thì thực hiện cập nhật
            if (plan.getCampains().size() > 0) {
                PlanDBContext db = new PlanDBContext();
                db.update(plan); // Sửa thành cập nhật thay vì insert mới
                resp.getWriter().println("Your plan has been updated!");
            } else {
                resp.getWriter().println("Your plan does not have any products / campaigns.");
            }
        } catch (NumberFormatException e) {
            resp.getWriter().println("Invalid input format. Please check your data.");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("An error occurred while updating the plan. Please try again.");
        }
    }
}
