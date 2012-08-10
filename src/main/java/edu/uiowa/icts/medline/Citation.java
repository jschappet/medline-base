package edu.uiowa.icts.medline;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import edu.uiowa.medline.xml.Abstract;
import edu.uiowa.medline.xml.AbstractText;
import edu.uiowa.medline.xml.Affiliation;
import edu.uiowa.medline.xml.Article;
import edu.uiowa.medline.xml.ArticleTitle;
import edu.uiowa.medline.xml.Author;
import edu.uiowa.medline.xml.AuthorList;
import edu.uiowa.medline.xml.Chemical;
import edu.uiowa.medline.xml.CommentsCorrections;
import edu.uiowa.medline.xml.CommentsCorrectionsList;
import edu.uiowa.medline.xml.DescriptorName;
import edu.uiowa.medline.xml.Journal;
import edu.uiowa.medline.xml.JournalIssue;
import edu.uiowa.medline.xml.Keyword;
import edu.uiowa.medline.xml.KeywordList;
import edu.uiowa.medline.xml.MedlineCitation;
import edu.uiowa.medline.xml.MeshHeading;
import edu.uiowa.medline.xml.OtherAbstract;

public class Citation {
	
	private String pmid;
	private String journalTitle;
	private String articleTitle;
	private StringBuffer abstractText;

	private Date created;
	private Date completed;
	private Date revised;
	
	private Date published;
	
	
	private List<String[]> authorList;
	
	private List<String[]> citationList;
	private List<String> keywordList;

	private String affiliation;

	private List<String> chemicalList;
		

	private List<String> majorMeshHeading;
	private List<String> minorMeshHeading;
	
	private static JAXBContext jContext;
	private static Unmarshaller unmarshaller;
    private static final Logger LOG = Logger.getLogger(Citation.class);


	public Citation(String s, Unmarshaller unmarshaller ) {
		
		
		InputStream is = null;
		if (s != null) {
			try {
				is = new ByteArrayInputStream(s.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				LOG.error("UnsupportedEncodingException Message", e1);
				//e1.printStackTrace();
			}
		
		    try {
				MedlineCitation mlCitation = (edu.uiowa.medline.xml.MedlineCitation)unmarshaller.unmarshal(is);
				init(mlCitation);
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				LOG.error("Error Message", e);
				//LOG.info("XML: " + s);
				//e.printStackTrace();
			}catch (JAXBException e) {
				// TODO Auto-generated catch block
				LOG.error("Error Message", e);
				LOG.info("XML: " + s);
				//e.printStackTrace();
			}
		}
	}

    
	public Citation(String s) {
		
		
		InputStream is = null;
		if (s != null) {
			try {
				is = new ByteArrayInputStream(s.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				//LOG.error("Error Message", e1);
				//e1.printStackTrace();
			}
		
		    try {
				jContext=JAXBContext.newInstance("edu.uiowa.medline.xml");
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				LOG.error("Error Message", e);
				//e.printStackTrace();
			} 
			
			try {
				unmarshaller = jContext.createUnmarshaller() ;
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				LOG.error("Error Message", e);
				//e.printStackTrace();
			
			} 
		    try {
				MedlineCitation mlCitation = (edu.uiowa.medline.xml.MedlineCitation)unmarshaller.unmarshal(is);
				init(mlCitation);
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				LOG.error("Error Message", e);
				//LOG.info("XML: " + s);
				//e.printStackTrace();
			}catch (JAXBException e) {
				// TODO Auto-generated catch block
				LOG.error("Error Message", e);
				LOG.info("XML: " + s);
				//e.printStackTrace();
			}
		}
	}
	
	public Citation(MedlineCitation citation) {
		init(citation);
	}
	
	public void init(MedlineCitation citation) {
		
		
		this.pmid = citation.getPMID().getContent();
		
		
		this.abstractText = new StringBuffer();
		this.majorMeshHeading = new ArrayList<String>();
		this.minorMeshHeading = new ArrayList<String>();
		this.authorList = new ArrayList<String[]>();
		this.citationList = new ArrayList<String[]>();
		this.keywordList = new ArrayList<String>();
		
		this.chemicalList=new ArrayList<String>();

		try {
			this.created= citation.getDateCreated().getDate() ;
		} catch (NullPointerException e3) {
		
			this.created = null;
		}
		try {
			this.completed = citation.getDateCompleted().getDate();
		} catch (NullPointerException e2) {
			
			this.completed = null;
		}
		try {
			this.revised =  citation.getDateRevised().getDate();
		} catch (NullPointerException e1) {
			
			this.revised = null;
			
		}
		
		Article article = citation.getArticle();
		Iterator aIter = article.getContent().iterator();
		while(aIter.hasNext()) {
			Object next = aIter.next();
			if ("edu.uiowa.medline.xml.Journal".equals(next.getClass().getName())) {
				
				Journal journal = (Journal)next;
				this.setJournalTitle(journal.getTitle().getContent());
				JournalIssue issue = journal.getJournalIssue();
				this.setPublished(issue.getPubDate().getDate());
				
			} else if ("edu.uiowa.medline.xml.ArticleTitle".equals(next.getClass().getName())) {

				ArticleTitle title = (ArticleTitle)next;
				this.setArticleTitle(title.getContent()); 
				
			} else if ("edu.uiowa.medline.xml.Pagination".equals(next.getClass().getName())) {
			} else if ("edu.uiowa.medline.xml.Affiliation".equals(next.getClass().getName())) {
				
				Affiliation aff = (Affiliation)next;
				this.affiliation = aff.getContent();
				
			}else if ("edu.uiowa.medline.xml.Abstract".equals(next.getClass().getName())) {
				Abstract abstrText = (Abstract)next;
				
						
				List<AbstractText> abstrList = abstrText.getAbstractText();
				
				for (AbstractText abstr : abstrList) {
					this.abstractText.append( abstr.getContent() + "\n");
					
				}		
				List<OtherAbstract> otherAbstrList = citation.getOtherAbstract();
				for (OtherAbstract otherAbstr : otherAbstrList) {
					List<AbstractText> list =  otherAbstr.getAbstractText();
					for (AbstractText abstrTxt : list) {
						this.abstractText.append(abstrTxt.getContent() +"\n" );
					}
				}				
				
			} else if ("edu.uiowa.medline.xml.AuthorList".equals(next.getClass().getName())) {
				
				AuthorList content = (AuthorList)next;
				List<Author> authList = content.getAuthor();
				for (Author auth : authList) {
					String[] author = new String[3];
					author[0] =  auth.getLastName() != null ? auth.getLastName().getContent() : "";
					author[1] =  auth.getForeName() != null ? auth.getForeName().getContent() : "";
					author[2] =  auth.getInitials() != null ? auth.getInitials().getContent() : "";
					this.authorList.add(author) ;
				}
				
			} else if ("edu.uiowa.medline.xml.Language".equals(next.getClass().getName())) {
				
			} else if ("edu.uiowa.medline.xml.PublicationTypeList".equals(next.getClass().getName())) {
			}
		}
		try {
			CommentsCorrectionsList citationSet = citation.getCommentsCorrectionsList();
			if (citationSet != null) {
				List<CommentsCorrections> citationCorrectionsList = citationSet.getCommentsCorrections();
				for (CommentsCorrections cc : citationCorrectionsList ) {
					String[] cit = new String[3];
					cit[0] = cc.getRefSource().getContent();
					cit[1] = cc.getPMID().getContent();
					cit[2] = cc.getRefType();
					citationList.add(cit);
				}
			}
		}catch (Exception e) {
			this.citationList.add(new String[3]);
		}
		try {
			List<MeshHeading> mhl = citation.getMeshHeadingList().getMeshHeading();
			for (MeshHeading heading : mhl) {
				DescriptorName descriptorName = heading.getDescriptorName();
				if ("Y".equals(descriptorName.getMajorTopicYN())) {
						this.majorMeshHeading.add(heading.getDescriptorName().getContent());
					} else {
						this.minorMeshHeading.add(heading.getDescriptorName().getContent());
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.majorMeshHeading.add("No Mesh Terms");
			this.minorMeshHeading.add("No Mesh Terms");
			//e.printStackTrace();
		}
		
		
		try {
			List<Chemical> chemList = citation.getChemicalList().getChemical();
			for (Chemical chem : chemList) {
				this.chemicalList.add(chem.getNameOfSubstance().getContent()); 
			}
		} catch (NullPointerException e) {
			
			this.chemicalList.add("No Chemlicals Listed");
			
		}
		try {
			
			List<KeywordList> keywordList = citation.getKeywordList();
			for (KeywordList kwl : keywordList) {
				List<Keyword> list =  kwl.getKeyword();
				for (Keyword keyword : list) {
					this.keywordList.add(keyword.getContent());
				}
			}
				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.keywordList.add("No Keywords");
			
			//e.printStackTrace();
		}
		
	}


	public String getJournalTitle() {
		return journalTitle;
	}


	public void setJournalTitle(String journalTitle) {
		this.journalTitle = journalTitle;
	}


	public String getArticleTitle() {
		return articleTitle;
	}


	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}


	public String getPmid() {
		return pmid;
	}


	public void setPmid(String pmid) {
		this.pmid = pmid;
	}
	
	
	public String getAbstractText() {
		return abstractText != null ? abstractText.toString() : "";
	}


	public void setAbstractText(StringBuffer abstractText) {
		this.abstractText = abstractText;
	}


	public List<String> getMajorMeshHeading() {
		return majorMeshHeading;
	}


	public void setMajorMeshHeading(List<String> majorMeshHeading) {
		this.majorMeshHeading = majorMeshHeading;
	}


	public List<String> getMinorMeshHeading() {
		return minorMeshHeading;
	}


	public void setMinorMeshHeading(List<String> minorMeshHeading) {
		this.minorMeshHeading = minorMeshHeading;
	}


	public Date getCreated() {
		return created;
	}


	public Date getCompleted() {
		return completed;
	}


	public Date getRevised() {
		return revised;
	}
	public List<String[]> getAuthorList() {
		return authorList;
	}


	public void setAuthorList(List<String[]> authorList) {
		this.authorList = authorList;
	}
	

	public List<String> getKeywordList() {
		return keywordList;
	}


	public void setKeywordList(List<String> keywordList) {
		this.keywordList = keywordList;
	}
	
	
	public List<String> getChemicalList() {
		return chemicalList;
	}


	public void setChemicalList(List<String> chemicalList) {
		this.chemicalList = chemicalList;
	}

	
	

	public String getAffiliation() {
		return affiliation;
	}


	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}


	public Date getPublished() {
		return published;
	}


	public void setPublished(Date published) {
		this.published = published;
	}


	public List<String[]> getCitationList() {
		return citationList;
	}


	public void setCitationList(List<String[]> citationList) {
		this.citationList = citationList;
	}
}
