package lzketh.model;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionService {

	public static Question add(Question question) {
		int id;
		ArrayList<Question> all = load();
		if (all.isEmpty()){
			id = 1;
		} else {
			Question last = all.get(all.size() - 1);
			id = last.id + 1;
		}

		question.id = id;
		all.add(question);
		save(all);
		return question;
	}

	public static ArrayList<Question> load() {
		String className = Question.class.getSimpleName();

		ArrayList<Question> rs = ModelFactory.load(className, 7, modelData -> {
			Integer id = Integer.parseInt(modelData.get(0));
			String title = modelData.get(1);
			String A = modelData.get(2);
			String B = modelData.get(3);
			String C = modelData.get(4);
			String D = modelData.get(5);
			String solution = modelData.get(6);

			ArrayList<String> list = new ArrayList<>();
			list.add(A);
			list.add(B);
			list.add(C);
			list.add(D);

			Question q = new Question();
			q.id = id;
			q.title = title;
			q.options = list;
			q.solution = solution;

			return q;
		});
		return rs;
	}

	public static void save(ArrayList<Question> list) {
		String className = Question.class.getSimpleName();

		ModelFactory.save(className, list, model -> {
			ArrayList<String> lines = new ArrayList<>();
			lines.add(model.id.toString());
			lines.add(model.title);
			lines.addAll(model.options);
			lines.add(model.solution);
			return lines;
		});
	}

}
