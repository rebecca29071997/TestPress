package testPressQuiz;
import java.io.*;
	import java.util.*;

	public class TestQuiz {

		static List<Student> ls=new ArrayList<Student>();
		static List<Quiz> lq=new ArrayList<Quiz>();
		static List<Teacher> lt=new ArrayList<Teacher>();
		
		static BufferedReader s =  
	            new BufferedReader(new InputStreamReader(System.in)); 
		
		static
		{
			System.out.println("+-----------------------------+");
			System.out.print("| Welcome to"); 
			System.err.print(" Qspiders ");
			System.out.println(" E-quiz |");
			System.out.println("+-----------------------------+");
		}
		
		static
		{
			Teacher t=new Teacher("Rebecca", 101);
			lt.add(t);
			
			Student  s=new Student("Priya", 1, 'A', null);
			
			try {
				refresh();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		@SuppressWarnings("unchecked")
		public static List<Quiz> getQuizes() throws Exception
		{
			List<Quiz> Q;
			FileInputStream fis = new FileInputStream("Quiz");
			ObjectInputStream ois=new ObjectInputStream( fis );
			
			Q= (List<Quiz>) ois.readObject();
			
			return Q;
		}
		
		@SuppressWarnings("unchecked")
		public static List<Student> getStudents() throws Exception
		{
			List<Student> Q;
			FileInputStream fis = new FileInputStream("Student");
			ObjectInputStream ois=new ObjectInputStream( fis );
			
			Q= (List<Student>) ois.readObject();
			
			return Q;
		}
		
		@SuppressWarnings("unchecked")
		public static List<Teacher> getTeachers() throws Exception
		{
			List<Teacher> Q;
			FileInputStream fis = new FileInputStream("Teacher");
			ObjectInputStream ois=new ObjectInputStream( fis );
			
			Q= (List<Teacher>) ois.readObject();
			
			return Q;
		}
		
		public static void refresh() throws Exception
		{
			refreshWrite();
			refreshRead();
		}
		
		public static void refreshRead() throws Exception
		{
			ls=getStudents();
			lq=getQuizes();
			lt=getTeachers();
		}
		
		public static void refreshWrite() throws Exception
		{
			FileOutputStream fos1=new FileOutputStream("Quiz"),
					fos2=new FileOutputStream("Student"),
					fos3=new FileOutputStream("Teacher");
			
			ObjectOutputStream oos1=new ObjectOutputStream(fos1)
					, oos2=new ObjectOutputStream(fos2),
							oos3=new ObjectOutputStream(fos3);
			
			oos1.writeObject(lq);
			oos2.writeObject(ls);
			oos3.writeObject(lt);
		}
		
		public static void createQuiz(Teacher t) throws Exception
		{
			refresh();
		
			Quiz q=new Quiz();

			System.out.print("Subject Name : ");	
			String sub=s.readLine();
			
			q.Subject=sub;
			q.CreatedBy=t.name;
			System.out.println();
			for(int i=0;i<=10;i++)
			{
				Question qs =new Question();
				
				
				System.out.println("Enter the question");
				qs.question=s.readLine();
				
				System.out.println("Enter the answers");
				int j;
				Map<Integer, String> ans;
				for(j=0, ans= qs.Choices ; j<4 ;j++)
				{	
					System.out.println(j+1 +" : ");
					String a=s.readLine();
					System.out.println();
					ans.put(j+1, a);
				}
				System.out.println("Enter the correct option (Enter X to stop)");
				
				while( true )
				{
					String answer = s.readLine();
					if(answer.equalsIgnoreCase("X"))
						break;
					else
					{
						qs.correctChoice.add(Integer.parseInt(answer));
					}
				}
				q.s.add(qs);
			}
			
			lq.add(q);
			refresh();
		}
		
		public static void main(String[] args) throws Exception 
		
		{
			System.out.println("Please Enter the details");
			System.out.println("Are you a Student or a Teacher");
			String op1=s.readLine();
			
			if(op1.equalsIgnoreCase("Student"))
			{
				System.out.println("**************");
				System.out.println("Please select an option");
				System.out.println("\n"
									+"1: Take a Quiz\n"
									+"2: View your details");
				switch(Integer.parseInt(s.readLine()))
				{
					case 1	:
					{
							System.out.println("Enter the Subject Name");
							String SubjectName=s.readLine();
							for(Quiz q: lq )
							{
								if( q.getSubject().equalsIgnoreCase(SubjectName) )
								{
									Student st=new Student();
									System.out.println("Enter your details");
									
									System.out.print("\n Name\t:\t");		
									st.Name=s.readLine();
										
									System.out.print("\n Roll_no\t:\t"); 	
									st.roll_no=Integer.parseInt(s.readLine());
									
									System.out.print("\n Section\t:\t");	
									st.section=s.readLine().charAt(0);
									
									System.out.println();
									
									refresh();
									takeQuiz(q,st);
								}
								else
									System.out.println("Quiz not available");
							}
							
					}
					break;
					
					case 2:
					{
						System.out.println("Enter your Name and Roll no");
						Student st=new Student();
						
						st.Name=s.readLine();
						st.roll_no=Integer.parseInt(s.readLine());
						
						for(Student s : ls)
						{
							if( s.Name.equals(st.Name) && s.roll_no == st.roll_no)
								System.out.println(s);
						}
					}
					break;
					
					default: System.out.println("Incorrect Option");
							
				}
				System.out.println("**************");
			}
			else if(op1.equalsIgnoreCase("teacher"))
			{
				System.out.println("Please Login to continue");
				Teacher t=new Teacher();
				t.name=s.readLine();
				t.id=Integer.parseInt(s.readLine());
				for( Teacher tr : lt)
				{
					if( tr.name.equalsIgnoreCase(t.name) && tr.id == t.id)
					{
						System.out.println("-------------------");
						System.out.println("Please choose an option");
						System.out.println("1: create quiz\n2: View student scores\n");
						switch(Integer.parseInt(s.readLine()))
						{
							case 1: createQuiz(t);  break;
							
							case 2: 
							{
								for(Student stu : ls)
								{
									System.out.println("\n"+stu+"\n");
								}
							}
							break;
							
							default: System.out.println("Incorrect option");
						}
						System.out.println("-------------------");
					}
				}
			}
			else
				System.err.println("Incorrect option entered");
		}
		
		private static void takeQuiz(Quiz q, Student st) throws Exception 
		{
			int correctCount=0;
			System.out.println("+-----------------------+");
			System.out.println("|     All the best      |");
			System.out.println("+-----------------------+");
			
			
			for(Question qs : q.getS())
			{
				System.out.println(qs.question);
				System.out.println(qs.Choices);
				System.out.println("Your answer is");
				int an=Integer.parseInt(s.readLine());
				for(Integer i: qs.correctChoice)
				{
					if(i==an)
						correctCount++;
				}
			}
			
			st.quizTaken.put(q, correctCount);
			System.out.println("your total score is "+correctCount);
			ls.add(st);
			refresh();
		}

	}
	class Quiz implements Serializable
	{

		private static final long serialVersionUID = 1L;

		String Subject;
		String CreatedBy;
	    static Set<Question> s=new HashSet<Question>();
		public Quiz() {
			super();
		}
		public Quiz(String subject, String createdBy) {
			super();
			Subject = subject;
			CreatedBy = createdBy;
		}
		public String getSubject() {
			return Subject;
		}
		public void setSubject(String subject) {
			Subject = subject;
		}
		public String getCreatedBy() {
			return CreatedBy;
		}
		public void setCreatedBy(String createdBy) {
			CreatedBy = createdBy;
		}
		public static Set<Question> getS() {
			return s;
		}
		public static void setS(Set<Question> s) {
			Quiz.s = s;
		}

	    
	}
	class Student implements Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		String Name;
		int roll_no;
		char section;
		Map<Quiz, Integer> quizTaken=new LinkedHashMap<Quiz, Integer>();
		public Student() {
			super();
		}
		public Student(String name, int roll_no, char section, Map<Quiz, Integer> quizTaken) {
			super();
			Name = name;
			this.roll_no = roll_no;
			this.section = section;
			this.quizTaken = quizTaken;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public int getRoll_no() {
			return roll_no;
		}
		public void setRoll_no(int roll_no) {
			this.roll_no = roll_no;
		}
		public char getSection() {
			return section;
		}
		public void setSection(char section) {
			this.section = section;
		}
		public Map<Quiz, Integer> getQuizTaken() {
			return quizTaken;
		}
		public void setQuizTaken(Map<Quiz, Integer> quizTaken) {
			this.quizTaken = quizTaken;
		}
		
		public String toString() {
			return Name + "\nRoll_no =" + roll_no + "\nSection=" + section + "\nQuizTaken=" + quizTaken;
		}
		
		
	}

	class Question implements Serializable
	{
	    private static final long serialVersionUID = 1L;
		String question;
	    Map<Integer,String> Choices = new LinkedHashMap<Integer, String>();
	    Vector<Integer> correctChoice=new Vector<Integer>();
	    
	    public Question() {}
	    
	    
		public Question(String question, Map<Integer, String> choices, Vector<Integer> correctChoice) {
			super();
			this.question = question;
			Choices = choices;
			this.correctChoice = correctChoice;
		}

public String getQuestion() {
			return question;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public Map<Integer, String> getChoices() {
			return Choices;
		}
		public void setChoices(Map<Integer, String> choices) {
			Choices = choices;
		}
		public Vector<Integer> getCorrectChoice() {
			return correctChoice;
		}
		public void setCorrectChoice(Vector<Integer> correctChoice) {
			this.correctChoice = correctChoice;
		}
			public String toString() {
			return "[ " + question + "]\n" + Choices;
		}
		
	}

	class Teacher implements Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		String name;
		int id;
		
		public Teacher() {}
		
		public Teacher(String name, int id) {
			super();
			this.name = name;
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		
	}

