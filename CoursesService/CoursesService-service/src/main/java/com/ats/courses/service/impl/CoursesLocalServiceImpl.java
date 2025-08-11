/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.ats.courses.service.impl;

import com.ats.courses.exception.NoSuchCoursesException;
import com.ats.courses.model.Courses;
import com.ats.courses.service.base.CoursesLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(property = "model.class.name=com.ats.courses.model.Courses", service = AopService.class)
public class CoursesLocalServiceImpl extends CoursesLocalServiceBaseImpl {

	@Override
	public Courses addCourses(String Courses_name, String Courses_des, String Courses_type, String Courses_rating,
			InputStream Courses_imgStream) {
		System.out.println("Service Coursesadd Method inside :::");
		long coursesId = counterLocalService.increment(Courses.class.getName());
		Courses objCourses = coursesPersistence.create(coursesId);
		try {
			objCourses.setCourses_name(Courses_name);
			objCourses.setCourses_des(Courses_des);
			objCourses.setCourses_type(Courses_type);
			objCourses.setCourses_rating(Courses_rating);
			objCourses.setCreateDate(new Date());

			String CoursesImgBase64;
			CoursesImgBase64 = convertImageToBase64(Courses_imgStream);
			objCourses.setCourses_img(CoursesImgBase64);

			System.out.println("Courses Name 	   ::" + Courses_name);
			System.out.println("Courses des  	   ::" + Courses_des);
			System.out.println("Courses type 	   ::" + Courses_type);
			System.out.println("Courses rating     ::" + Courses_rating);
			System.out.println("Courses imgStream  ::" + Courses_imgStream);
			coursesLocalService.addCourses(objCourses);
			return super.addCourses(objCourses);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objCourses;

	}

	public String convertImageToBase64(InputStream inputStream) throws java.io.IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		byte[] imageBytes = outputStream.toByteArray();
		return Base64.getEncoder().encodeToString(imageBytes);
	}

	@Override
	public Courses deleteCourses(long coursesId) throws NoSuchCoursesException {
		Courses deleteCourses = coursesPersistence.findByPrimaryKey(coursesId);
		return super.deleteCourses(deleteCourses);
	}

	@Override
	public Courses updateCourses(long coursesId, String Courses_name, String Courses_des, String Courses_type,
			String Courses_rating, InputStream Courses_imgStream) throws PortalException {

		Courses objCourses = coursesPersistence.findByPrimaryKey(coursesId);

		objCourses.setCourses_name(Courses_name);
		objCourses.setCourses_des(Courses_des);
		objCourses.setCourses_type(Courses_type);
		objCourses.setCourses_rating(Courses_rating);
		objCourses.setModifiedDate(new Date());

		if (Courses_imgStream != null) {
			String coursesPhotoBase64;
			try {
				coursesPhotoBase64 = convertImageToBase64(Courses_imgStream);
				objCourses.setCourses_img(coursesPhotoBase64);
			} catch (IOException e) {
				throw new PortalException("Error processing the product photo", e);
			}
		}
		objCourses = coursesPersistence.update(objCourses);

		return super.updateCourses(objCourses);
	}

	 // custom sql
		public List<Courses> GetAllCoursesTypes(String Courses_type) {
			return coursesFinder.GetAllCoursesTypes(Courses_type);
		}
	
		
}