/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package fironlineser.service.impl;

import com.liferay.portal.aop.AopService;

import fironlineser.service.base.FIRRRLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=fironlineser.model.FIRRR",
	service = AopService.class
)
public class FIRRRLocalServiceImpl extends FIRRRLocalServiceBaseImpl {
}