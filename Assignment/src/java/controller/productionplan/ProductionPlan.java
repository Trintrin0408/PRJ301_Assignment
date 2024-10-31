package controller.productionplan;

import controller.accesscontrol.BaseRBACController;
import dal.PlanDBContext;
import entity.Plan;
import entity.accesscontrol.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ProductionPlan extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        PlanDBContext planDB = new PlanDBContext();

        // Get plans by status using the updated PlanDBContext
        ArrayList<Plan> planListToDo = planDB.getPlansByStatus("todo");
        ArrayList<Plan> planListDoing = planDB.getPlansByStatus("doing");
        ArrayList<Plan> planListDone = planDB.getPlansByStatus("done");
        ArrayList<Plan> planListLate = planDB.getPlansByStatus("late");

        // Set the fetched lists as attributes for the JSP page
        req.setAttribute("planListToDo", planListToDo);
        req.setAttribute("planListDoing", planListDoing);
        req.setAttribute("planListDone", planListDone);
        req.setAttribute("planListLate", planListLate);
        Logger logger = Logger.getLogger(ProductionPlan.class.getName());
        logger.info("Plan List To Late: " + planListLate.size());
        for (Plan plan : planListToDo) {
            logger.info("Plan Name (To Do): " + plan.getName());
        }
        resp.getWriter().println("login successful!");
        // Forward the request to the JSP (plan.jsp)
        req.getRequestDispatcher("../view/productionplan/plan.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        // Post functionality if required later (currently unused)
    }
}
