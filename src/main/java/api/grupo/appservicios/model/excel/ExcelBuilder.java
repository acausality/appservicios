package api.grupo.appservicios.model.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import api.grupo.appservicios.model.dto.ClientDTO;
import api.grupo.appservicios.model.dto.ProfessionalDTO;

public class ExcelBuilder /* extends AbstractXlsxStreamingView */ {

	public static void build(List<ClientDTO> clients, List<ProfessionalDTO> professionals, File file)
			throws IOException {
		/** Instancia para leer/escribir un archivo excel */
		Workbook workbook = new XSSFWorkbook();

		/** Creo las hojas del excel */
		Sheet clientSheet = workbook.createSheet("Clientes");
		Sheet professionalSheet = workbook.createSheet("Profesionales");

		clientSheet.setDefaultColumnWidth(30);
		professionalSheet.setDefaultColumnWidth(30);

		/** Estilo para las celdas del encabezado */
		CellStyle headerStyle = setStyle(workbook, BorderStyle.MEDIUM, HSSFColorPredefined.LIGHT_GREEN,
				FillPatternType.SOLID_FOREGROUND, 11, true);

		/** Estilo para las demás celdas */
		CellStyle style = setStyle(workbook, BorderStyle.THIN, HSSFColorPredefined.WHITE, FillPatternType.NO_FILL, 11,
				false);

		/** Estilos para las celdas de la última fila */
		CellStyle lastRowStyleCell0 = setStyle(workbook, BorderStyle.THIN, HSSFColorPredefined.LIGHT_YELLOW,
				FillPatternType.SOLID_FOREGROUND, 11, false);
		CellStyle lastRowStyleCell1 = setStyle(workbook, BorderStyle.THIN, HSSFColorPredefined.LIGHT_YELLOW,
				FillPatternType.SOLID_FOREGROUND, 22, true);

		/** Relleno el encabezado de las hojas */
		createHeader(clientSheet.createRow(0), headerStyle, new String[] { "Id", "Nombre", "Apellido",
				"Tipo de documento", "Número de documento", "Dirección", "Teléfono", "Email" });
		createHeader(professionalSheet.createRow(0), headerStyle, new String[] { "Id", "Nombre", "Apellido",
				"Tipo de documento", "Número de documento", "Dirección", "Teléfono", "Email" });

		/**
		 * Relleno las celdas con los datos de los clientes y profesionales registrados
		 * el día de hoy
		 */
		int rowCount = 1;
		Row row = null;
		for (ClientDTO client : clients) {
			row = clientSheet.createRow(rowCount++);
			row.createCell(0).setCellValue(client.getId());
			row.getCell(0).setCellStyle(style);
			row.createCell(1).setCellValue(client.getName());
			row.getCell(1).setCellStyle(style);
			row.createCell(2).setCellValue(client.getSurname());
			row.getCell(2).setCellStyle(style);
			row.createCell(3).setCellValue(client.getIdentityType());
			row.getCell(3).setCellStyle(style);
			row.createCell(4).setCellValue(client.getIdentityNumber());
			row.getCell(4).setCellStyle(style);
			row.createCell(5).setCellValue(client.getAddress());
			row.getCell(5).setCellStyle(style);
			row.createCell(6).setCellValue(client.getPhoneNumber());
			row.getCell(6).setCellStyle(style);
			row.createCell(7).setCellValue(client.getEmail());
			row.getCell(7).setCellStyle(style);
		}

		/** Relleno la última fila con la cantidad total de clientes dados de alta */
		row = clientSheet.createRow(++rowCount);
		row.createCell(0).setCellValue("Cantidad de clientes dados de alta:");
		row.createCell(1).setCellValue(clients.size());
		row.getCell(0).setCellStyle(lastRowStyleCell0);
		row.getCell(1).setCellStyle(lastRowStyleCell1);

		rowCount = 1;
		for (ProfessionalDTO professional : professionals) {
			row = professionalSheet.createRow(rowCount++);
			row.createCell(0).setCellValue(professional.getId());
			row.getCell(0).setCellStyle(style);
			row.createCell(1).setCellValue(professional.getName());
			row.getCell(1).setCellStyle(style);
			row.createCell(2).setCellValue(professional.getSurname());
			row.getCell(2).setCellStyle(style);
			row.createCell(3).setCellValue(professional.getIdentityType());
			row.getCell(3).setCellStyle(style);
			row.createCell(4).setCellValue(professional.getIdentityNumber());
			row.getCell(4).setCellStyle(style);
			row.createCell(5).setCellValue(professional.getAddress());
			row.getCell(5).setCellStyle(style);
			row.createCell(6).setCellValue(professional.getPhoneNumber());
			row.getCell(6).setCellStyle(style);
			row.createCell(7).setCellValue(professional.getEmail());
			row.getCell(7).setCellStyle(style);
		}

		/**
		 * Relleno la última fila con la cantidad total de profesionales dados de alta
		 */
		row = professionalSheet.createRow(++rowCount);
		row.createCell(0).setCellValue("Cantidad de profesionales dados de alta:");
		row.createCell(1).setCellValue(professionals.size());
		row.getCell(0).setCellStyle(lastRowStyleCell0);
		row.getCell(1).setCellStyle(lastRowStyleCell1);

		/**
		 * Finalmente escribo el archivo, libero los recursos asociados y cierro el
		 * workbook
		 */
		OutputStream excelOutput = new FileOutputStream(file);
		workbook.write(excelOutput);
		excelOutput.close();
		workbook.close();
	}

	private static void createHeader(Row header, CellStyle style, String[] titles) {
		int i = 0;
		for (String title : titles) {
			header.createCell(i).setCellValue(title);
			header.getCell(i).setCellStyle(style);
			i++;
		}
	}

	private static CellStyle setStyle(Workbook workbook, BorderStyle borderStyle, HSSFColorPredefined color,
			FillPatternType fillPatternType, int fontSize, boolean bold) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) fontSize);
		style.setFillForegroundColor(color.getIndex());
		style.setFillPattern(fillPatternType);
		style.setBorderBottom(borderStyle);
		style.setBorderLeft(borderStyle);
		style.setBorderRight(borderStyle);
		style.setBorderTop(borderStyle);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		font.setBold(bold);

		return style;
	}
/*
	public static void buildClientList(List<ClientDTO> clients) throws IOException {
		File file = new File("reports/client-list.xlsx");
		
		if (!file.exists()) {
			file.getParentFile().mkdir();
			file.createNewFile();
		}
		
		if (!file.isDirectory()) {
			OutputStream excelOutputStream = new FileOutputStream(file);
			
			Workbook workbook = new XSSFWorkbook();
			
			// Hoja excel
			Sheet sheet = workbook.createSheet("Clientes");
			sheet.setDefaultColumnWidth(30);
	
			// Estilo para las celdas del encabezado
			CellStyle headerStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setFontName("Calibri");
			headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setBorderBottom(BorderStyle.MEDIUM);
			headerStyle.setBorderLeft(BorderStyle.MEDIUM);
			headerStyle.setBorderRight(BorderStyle.MEDIUM);
			headerStyle.setBorderTop(BorderStyle.MEDIUM);
			headerStyle.setFont(headerFont);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);
			headerFont.setBold(true);
			
			// Estilos para las celdas
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setFontName("Calibri");
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.CENTER);
	
			// Encabezado
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Id");
			header.getCell(0).setCellStyle(headerStyle);
			header.createCell(1).setCellValue("Nombre");
			header.getCell(1).setCellStyle(headerStyle);
			header.createCell(2).setCellValue("Apellido");
			header.getCell(2).setCellStyle(headerStyle);
			header.createCell(3).setCellValue("Tipo de documento");
			header.getCell(3).setCellStyle(headerStyle);
			header.createCell(4).setCellValue("Número de documento");
			header.getCell(4).setCellStyle(headerStyle);
			header.createCell(5).setCellValue("Dirección");
			header.getCell(5).setCellStyle(headerStyle);
			header.createCell(6).setCellValue("Teléfono");
			header.getCell(6).setCellStyle(headerStyle);
			header.createCell(7).setCellValue("Email");
			header.getCell(7).setCellStyle(headerStyle);
			
			// Rellenar celdas
			int rowCount = 1;
			for (ClientDTO client : clients) {
				Row courseRow = sheet.createRow(rowCount++);
				courseRow.createCell(0).setCellValue(client.getId());
				courseRow.getCell(0).setCellStyle(style);
				courseRow.createCell(1).setCellValue(client.getName());
				courseRow.getCell(1).setCellStyle(style);
				courseRow.createCell(2).setCellValue(client.getSurname());
				courseRow.getCell(2).setCellStyle(style);
				courseRow.createCell(3).setCellValue(client.getIdentityType());
				courseRow.getCell(3).setCellStyle(style);
				courseRow.createCell(4).setCellValue(client.getIdentityNumber());
				courseRow.getCell(4).setCellStyle(style);
				courseRow.createCell(5).setCellValue(client.getAddress());
				courseRow.getCell(5).setCellStyle(style);
				courseRow.createCell(6).setCellValue(client.getPhoneNumber());
				courseRow.getCell(6).setCellStyle(style);
				courseRow.createCell(7).setCellValue(client.getEmail());
				courseRow.getCell(7).setCellStyle(style);
			}
			
			workbook.write(excelOutputStream);
			excelOutputStream.close();
			workbook.close();
		}
	}
	*/
/*
	public static void buildProfessionalList(List<ProfessionalDTO> professionals) throws IOException {
		File file = new File("reports/professional-list.xlsx");
		
		if (!file.exists()) {
			file.getParentFile().mkdir();
			file.createNewFile();
		}
		
		if (!file.isDirectory()) {
			OutputStream excelOutputStream = new FileOutputStream(file);
			
			Workbook workbook = new XSSFWorkbook();
			
			// Hoja excel
			Sheet sheet = workbook.createSheet("Profesionales");
			sheet.setDefaultColumnWidth(30);
	
			// Estilo para las celdas del encabezado
			CellStyle headerStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setFontName("Calibri");
			headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setBorderBottom(BorderStyle.MEDIUM);
			headerStyle.setBorderLeft(BorderStyle.MEDIUM);
			headerStyle.setBorderRight(BorderStyle.MEDIUM);
			headerStyle.setBorderTop(BorderStyle.MEDIUM);
			headerStyle.setFont(headerFont);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);
			headerFont.setBold(true);
			
			// Estilos para las celdas
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setFontName("Calibri");
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.CENTER);
	
			// Encabezado
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Id");
			header.getCell(0).setCellStyle(headerStyle);
			header.createCell(1).setCellValue("Nombre");
			header.getCell(1).setCellStyle(headerStyle);
			header.createCell(2).setCellValue("Apellido");
			header.getCell(2).setCellStyle(headerStyle);
			header.createCell(3).setCellValue("Tipo de documento");
			header.getCell(3).setCellStyle(headerStyle);
			header.createCell(4).setCellValue("Número de documento");
			header.getCell(4).setCellStyle(headerStyle);
			header.createCell(5).setCellValue("Dirección");
			header.getCell(5).setCellStyle(headerStyle);
			header.createCell(6).setCellValue("Teléfono");
			header.getCell(6).setCellStyle(headerStyle);
			header.createCell(7).setCellValue("Email");
			header.getCell(7).setCellStyle(headerStyle);
			
			// Rellenar celdas
			int rowCount = 1;
			for (ProfessionalDTO professional : professionals) {
				Row courseRow = sheet.createRow(rowCount++);
				courseRow.createCell(0).setCellValue(professional.getId());
				courseRow.getCell(0).setCellStyle(style);
				courseRow.createCell(1).setCellValue(professional.getName());
				courseRow.getCell(1).setCellStyle(style);
				courseRow.createCell(2).setCellValue(professional.getSurname());
				courseRow.getCell(2).setCellStyle(style);
				courseRow.createCell(3).setCellValue(professional.getIdentityType());
				courseRow.getCell(3).setCellStyle(style);
				courseRow.createCell(4).setCellValue(professional.getIdentityNumber());
				courseRow.getCell(4).setCellStyle(style);
				courseRow.createCell(5).setCellValue(professional.getAddress());
				courseRow.getCell(5).setCellStyle(style);
				courseRow.createCell(6).setCellValue(professional.getPhoneNumber());
				courseRow.getCell(6).setCellStyle(style);
				courseRow.createCell(7).setCellValue(professional.getEmail());
				courseRow.getCell(7).setCellStyle(style);
			}
			
			workbook.write(excelOutputStream);
			excelOutputStream.close();
			workbook.close();
		}
	}
	*/
	/*
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//response.setHeader("Content-Disposition", "attachment; filename=client-list.xlsx");

		@SuppressWarnings("unchecked")
		List<ClientDTO> clients = (List<ClientDTO>) model.get("clientList");

		// Hoja excel
		Sheet sheet = workbook.createSheet("Clientes");
		sheet.setDefaultColumnWidth(30);

		// Estilo para las celdas del encabezado
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
		style.setFont(font);

		// Encabezado
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Id");
		header.getCell(0).setCellStyle(style);
		header.createCell(1).setCellValue("Nombre");
		header.getCell(1).setCellStyle(style);

		// Rellenar celdas
		int rowCount = 1;
		for (ClientDTO client : clients) {
			Row courseRow = sheet.createRow(rowCount++);
			courseRow.createCell(0).setCellValue(client.getId());
			courseRow.createCell(1).setCellValue(client.getName());
			// courseRow.createCell(2).setCellValue(DATE_FORMAT.format(course.getDate()));
		}
	}*/

}
