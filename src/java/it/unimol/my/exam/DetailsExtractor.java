package it.unimol.my.exam;

import it.unimol.my.requesterhtml.HTMLRequester;
import it.unimol.my.utils.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Inserire descrizione classe
 * 
 * @author Matteo Ianno
 */
public class DetailsExtractor implements ExtractorInterface {

	@Override
	public DetailedExam getDetails(String urlTarget, String examId) {
		// TODO yet2impl
		return null;
	}

	@Override
	public DetailedExam getDetails(String urlServlet, String username,
			String password, String examId) throws UnirestException {
		HTMLRequester requester = new HTMLRequester();
		String html = null;
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("adsce_id", examId);
			URL target = new URL(urlServlet);
			html = requester.get(target, parameters, username, password);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
			return null;
		}
		Document doc = Jsoup.parse(html);
		Elements tds = doc.select("td[class=tplTitolo]");
		String name = "/";
		if (tds != null && tds.size() == 1) {
			name = tds.get(0).text();
			name.replaceFirst("Attività Didattica: ", "");
			name.replaceFirst("\\[[0-9]*\\]", "");
			name.trim();
		}
		Elements tdsTplMaster = doc.select("td[class=tplMaster]");
		String year = "/";
		String dateString = "";
		String grade = "/";
		if (tdsTplMaster != null && tdsTplMaster.size() >= 5) {
			year = StringUtils.realTrim(tdsTplMaster.get(0).text());
			dateString = StringUtils.realTrim(tdsTplMaster.get(2).text());
			grade = StringUtils.realTrim(tdsTplMaster.get(3).text());
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (!dateString.equals("/")) {
			try {
				date = sdf.parse(dateString);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		}
		int sumCfu = 0;
		List<Details> examsDetails = new ArrayList<Details>();
		Elements detailTables = doc.select("table[class=detail_table]");
		if (detailTables != null && detailTables.size() == 1) {
			Elements detailRows = detailTables.select("tr");
			int sumHours = 0;
			for (int i = 1; i < detailRows.size(); i++) {
				Element row = detailRows.get(i);
				Elements detailTds = row.select("td");
				String moduleName = StringUtils.realTrim(detailTds.get(0)
						.text());
				String area = StringUtils.realTrim(detailTds.get(3).text());
				String cfuString = StringUtils
						.realTrim(detailTds.get(4).text());
				int cfu = Integer.parseInt(cfuString);
				sumCfu += cfu;
				String hoursString = StringUtils.realTrim(detailTds.get(5)
						.text());
				int hours = Integer.parseInt(hoursString);
				sumHours += hours;
				Details details = new Details(moduleName, cfu, hours, area);
				examsDetails.add(details);
			}
		}
		DetailedExam detailedExam = new DetailedExam(name, sumCfu, grade, date,
				year, examId, examsDetails);
		return detailedExam;
	}

}
