package com.qiuxs.exam.action;

import com.opensymphony.xwork2.ActionContext;
import com.qiuxs.base.action.BaseAction;
import com.qiuxs.exam.entity.*;
import com.qiuxs.exam.service.IPublicService;
import com.qiuxs.base.util.MyReadExcel;
import com.qiuxs.base.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Scope("prototype")
public class PublicAction extends BaseAction {

	private static final long serialVersionUID = 353237989708931804L;


	private File file;
	private String fileFileName;
	private String fileContentType;
	private List<List<Object>> rows = new ArrayList<List<Object>>();

	private Grade grade;

	private Course course;

	private WordModel model;

	private List<Adminclass> adminclasses;
	private Adminclass adminclass;

	private List<Student> students;
	private Student student;

	private List<ExamBatch> examBatchs;
	private ExamBatch examBatch;


	@Autowired
	private IPublicService publicService;


	public String index(){


		return "index";
	}

	//========== 课程开始 ==========

	public String courseList(){


		List<Course> courses = publicService.getAll(Course.class);

		put("courses",courses);

		return "courseList";
	}

	public String courseEdit(){

		Course course = getEntity(Course.class,getInt("pair.id"));

		put("course",course);

		return "courseForm";
	}

	public void courseSave(){

		try {

			publicService.saveCourse(course);
			writeSuccese("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}
	public void courseRemove(){

		try {

			publicService.deleteCourse(getInt("pair.id"));
			writeSuccese("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}

	//========  课程结束 ============

	//========== 年级开始 ==========

	public String gradeList(){

		List<Grade> grades = publicService.getAll(Grade.class);
		put("grades",grades);

		return "gradeList";
	}

	public String gradeEdit(){

		grade = publicService.get(Grade.class,getInt("pair.id"));

		return "gradeForm";
	}

	public void gradeSave(){

		try {

			publicService.saveGrade(grade);
			ActionContext.getContext().getContextMap().get("parameters");
			writeSuccese("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}
	public void gradeRemove(){

		try {

			publicService.deleteGrade(getInt("pair.id"));
			writeSuccese("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}

	//========  年级结束 ============


	//========== 班级开始 ==========

	public String adminclassList(){

		adminclasses = publicService.getAll(Adminclass.class,"grade.name,order");

		return "adminclassList";
	}

	public String adminclassEdit(){

		List<Grade> grades = publicService.getAll(Grade.class);
		put("grades",grades);

		adminclass = publicService.get(Adminclass.class,getInt("pair.id"));

		return "adminclassForm";
	}

	public void adminclassSave(){

		try {

			publicService.saveAdminclass(adminclass);
			writeSuccese("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}
	public void adminclassRemove(){

		try {

			publicService.deleteAdminclass(getInt("pair.id"));
			writeSuccese("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}

	/**
	 * 上传班级
	 * @return
	 */
	public String adminclassUplaodSave(){
		//获取扩展名
		try {
			String ext = fileFileName.substring(fileFileName.lastIndexOf(".")+1);

			if(!"xls".equals(ext)&&!"xlsx".equals(ext)){//使用xls方式读取
				put("pair.state", "上传失败");
				put("pair.msg", "文件格式不正确（xls或xlsx）");
				return "adminclassUplaodForm";
			}

			List<List<String >> datas  = MyReadExcel.readExcel(file, fileFileName, -1, 0, 0, 0, 0) ;
			int count =  publicService.uplaodAdminclass(datas,getInt("pair.gradeId"));
			put("pair.state", "导入成功");
			put("pair.msg", "导入 "+count+" 个班级");
		} catch (Exception e) {
			put("pair.state", "导入失败");
			put("pair.msg", e.getMessage());
			e.printStackTrace();
		}
		List<Grade> grades = publicService.getAll(Grade.class);
		put("grades",grades);
		return "adminclassUplaodForm";
	}

	public String adminclassUplaodForm(){
		List<Grade> grades = publicService.getAll(Grade.class);
		put("grades",grades);
		return "adminclassUplaodForm";
	}


	//==========  班级结束 ==========

	//==========  学生开始 ==========

	public String studentList(){

		students = publicService.getAll(Student.class);

		return "studentList";
	}

	public String studentEdit(){


		List<Grade> grades = publicService.getAll(Grade.class);
		put("grades",grades);
		adminclasses = publicService.getAll(Adminclass.class);


		student = publicService.get(Student.class,getInt("pair.id"));

		return "studentForm";
	}

	public void studentSave(){

		try {

			publicService.saveStudent(student);
			writeSuccese("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}
	public void studentRemove(){

		try {

			publicService.deleteStudent(getInt("pair.id"));
			writeSuccese("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}

	/**
	 * 上传班级
	 * @return
	 */
	public String studentUplaodSave(){
		//获取扩展名
		try {
			String ext = fileFileName.substring(fileFileName.lastIndexOf(".")+1);

			if(!"xls".equals(ext)&&!"xlsx".equals(ext)){//使用xls方式读取
				put("pair.state", "上传失败");
				put("pair.msg", "文件格式不正确（xls或xlsx）");
				return "adminclassUplaodForm";
			}

			List<List<String >> datas  = MyReadExcel.readExcel(file, fileFileName, -1, 0, 0, 0, 0) ;
			int count = publicService.uplaodStudent(datas,getInt("pair.gradeId"));
			put("pair.state", "导入成功");
			put("pair.msg", "导入 "+count+" 个学生");
		} catch (Exception e) {
			put("pair.state", "导入失败");
			put("pair.msg", e.getMessage());
			e.printStackTrace();
		}
		List<Grade> grades = publicService.getAll(Grade.class);
		put("grades",grades);
		return "studentUplaodForm";
	}

	public String studentUplaodForm(){
		List<Grade> grades = publicService.getAll(Grade.class);
		put("grades",grades);
		return "studentUplaodForm";
	}

	//==========  学生结束 ==========


	//==========  开始批次开始 ==========

	public String examBatchList(){

		examBatchs = publicService.getAll(ExamBatch.class);

		return "examBatchList";
	}

	public String examBatchEdit(){


		List<Grade> grades = publicService.getAll(Grade.class);
		put("grades",grades);

		examBatch = publicService.get(ExamBatch.class,getInt("pair.id"));

		return "examBatchForm";
	}

	public void examBatchSave(){

		try {

			publicService.saveExamBatch(examBatch);
			writeSuccese("保存成功");
		} catch (Exception e) {
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}
	public void examBatchRemove(){

		try {
			publicService.deleteExamBatch(getInt("pair.id"));
			writeSuccese("删除成功");
		} catch (Exception e) {
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}
	}


	public String modelList(){

		List<WordModel> modelList = publicService.getAll(WordModel.class);

		put("modelList",modelList);

		return "modelList";
	}
	public String modelEdit(){

		WordModel model = getEntity(WordModel.class,getInt("pair.id"));

		put("model",model);

		return "modelForm";
	}

	public void modelSave() throws Exception {

		try {


			String indexStr = getString("index");

			if (Strings.isEmpty(indexStr)){
				writeFail("请维护阶段成绩");
				return;
			}

			List<Integer> indexList = Arrays.asList(Strings.splitToInt(indexStr));

			WordModel model = getEntity(WordModel.class,getInt("model.id"));
			model.setName(getString("model.name"));
			model.setCode(getString("model.code"));
			model.getLevels().clear();
			for (Integer index : indexList) {
				ScoreLevel level = new ScoreLevel();
				level.setModel(model);
				level.setName(getString("name_"+index));
				level.setMax(getDouble("max_"+index));
				level.setMin(getDouble("min_"+index));
				level.setPercent(getBoolean("percent_"+index));
				level.setSort(getInt("sort_"+index));
				model.getLevels().add(level);
			}
			publicService.saveModel(model);
			writeSuccese("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			writeFail(e.getMessage());
		}

	}

	public void modelRemove(){

		try {
			publicService.remove(WordModel.class,getInt("pair.id"));
			writeSuccese("删除成功");
		} catch (Exception e) {
			try {
				writeFail(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}




	//============get set=============//



	public List<ExamBatch> getExamBatchs() {
		return examBatchs;
	}

	public void setExamBatchs(List<ExamBatch> examBatchs) {
		this.examBatchs = examBatchs;
	}

	public ExamBatch getExamBatch() {
		return examBatch;
	}


	public void setExamBatch(ExamBatch examBatch) {
		this.examBatch = examBatch;
	}


	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}


	public List<Adminclass> getAdminclasses() {
		return adminclasses;
	}

	public void setAdminclasses(List<Adminclass> adminclasses) {
		this.adminclasses = adminclasses;
	}

	public Adminclass getAdminclass() {
		return adminclass;
	}

	public void setAdminclass(Adminclass adminclass) {
		this.adminclass = adminclass;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public List<List<Object>> getRows() {
		return rows;
	}

	public void setRows(List<List<Object>> rows) {
		this.rows = rows;
	}


	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public WordModel getModel() {
		return model;
	}

	public void setModel(WordModel model) {
		this.model = model;
	}
}
