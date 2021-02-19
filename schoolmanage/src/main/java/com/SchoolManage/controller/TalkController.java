package com.SchoolManage.controller;

import com.SchoolManage.pojo.AdminUser;
import com.SchoolManage.pojo.Talk;
import com.SchoolManage.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author RainGoal
 * @Date 2021/2/19 12:04
 * @Description TODO
 * @Version 1.0
 */
@Controller
@RequestMapping("talk")
public class TalkController {
    @Autowired
    private TalkService talkService;

    @RequestMapping("findall")
    @ResponseBody
    public List<Talk> findAll(Integer Page, HttpServletRequest request) {
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        return talkService.findAll(Page, a.getName());
    }

    @RequestMapping("findallcount")
    @ResponseBody
    public int findAllCount(HttpServletRequest request) {
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        return talkService.findAllCount(a.getName());
    }

    @RequestMapping("inserttalk")
    public String insertTalk(Talk talk, HttpServletRequest request) {
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        talk.setTeacher(a.getName());
        int i = talkService.insertTalk(talk);
        if (i != 0) {
            return "redirect:改变地址";
        } else return "redirect:改变地址";
    }

    @RequestMapping("findbystudentnopage")
    @ResponseBody
    public List<Talk> findByStudentNoPage(String student, HttpServletRequest request) {
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        return talkService.findByStudentNoPage(student, a.getName());
    }

    @RequestMapping("findbystudentpage")
    @ResponseBody
    public List<Talk> findByStudentPage(String student, Integer Page, HttpServletRequest request) {
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        return talkService.findByStudentPage(student, Page, a.getName());
    }

    @RequestMapping("findbystudentcount")
    @ResponseBody
    public int findByStudentCount(String student, HttpServletRequest request) {
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        return talkService.findByStudentCount(student, a.getName());
    }

    @RequestMapping("findbyteacher")
    @ResponseBody
    public List<Talk> findByTeacher(String teacher, Integer Page, Integer num) {
        return talkService.findByTeacher(teacher, Page, num);
    }

    @RequestMapping("deletetalk")
    @ResponseBody
    public Map<String, Object> deleteTalk(Integer id, HttpServletRequest request) {
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        int i = talkService.deleteTalk(id, a.getName());
        HashMap<String, Object> map = new HashMap<>();
        if (i != 0) {
            map.put("msg", "success");
            map.put("code", 200);
            return map;
        } else {
            map.put("msg", "failed");
            map.put("code", 500);
            return map;
        }
    }

    @RequestMapping("findbytime")
    @ResponseBody
    public List<Talk> findByTime(Date date, Integer Page, Integer num) {
        return talkService.findByTime(date, Page, num);
    }

    @RequestMapping("findbytimecount")
    @ResponseBody
    public int findByTimeCount(Date date, HttpServletRequest request) {
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        return talkService.findByTimeCount(date, a.getName());
    }

    @RequestMapping("findbytimeyearandmonth")
    @ResponseBody
    public List<Talk> findByTimeYearAndMonth(Date date, Integer Page, Integer num) {
        return talkService.findByTimeYearAndMonth(date, Page, num);
    }

    @RequestMapping("findbytimeyearandmonthcount")
    @ResponseBody
    public int findByTimeYearAndMonthCount(Date date) {
        return talkService.findByTimeYearAndMonthCount(date);
    }

    //这个接口用于绑定的时候验证权限
    @RequestMapping("checkuser")
    @ResponseBody
    public boolean check(Integer id, HttpServletRequest request) {
        Talk talk = talkService.findById(id);
        if (talk == null) return false;
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        if (talk.getTeacher().equals(a.getName())) return true;
        else return false;
    }

    @RequestMapping("findbyid")
    @ResponseBody
    public Talk findById(Integer id, HttpServletRequest request) {
        Talk talk = talkService.findById(id);
        if (talk == null) return null;
        AdminUser a = (AdminUser) request.getSession().getAttribute("administer");
        if (talk.getTeacher().equals(a.getName())) return talk;
        else return null;
    }
}
