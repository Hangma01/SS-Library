package book;

import java.util.Random;
import java.util.StringTokenizer;

public class Poem { //240603 11:00

	Random rand = new Random();
	int randomN;
	public String[][] poemList = { { "저렇게 많은 중에서/ 별 하나가 나를 내려다 본다/ 이렇게 많은 사람 중에서/ 그 별 하나를 쳐다본다 ...",	"이렇게 정다운/ 너 하나 나 하나는/ 어디서 무엇이 되어/ 다시 만나랴", "김광섭", "저녁에" },
								   { "별 하나에 쓸쓸함과/ 별 하나에 동경과/ 별 하나에 시와/ 별 하나에 어머니, 어머니, ...", "어머님,/ 나는 별 하나에 아름다운 말 한마디씩/ 불러 봅니다. ...", "윤동주", "별 헤는 밤"},
								   { "목련꽃 지는 모습 지저분하다고 말하지 말라/ 순백의 눈도 녹으면 질척거리는 것을/ 지는 모습까지 아름답기를 바라는가 ...", "그대를 향해 뿜었던 분수 같은 열정이/ 피딱지처럼 엉켜서/ 상처로 기억되는 그런 사랑일지라도/ 낫지 않고 싶어라/ 이대로 한 열흘만이라도 더 앓고 싶어라", "복효근", "목련 후기"},
								   { "내가 그의 이름을 불러 주기 전에는/ 그는 다만/ 하나의 몸짓에 지나지 않았다. ...", "내가 그의 이름을 불러 주었을 때/ 그는 나에게로 와서/ 꽃이 되었다.", "김춘수", "꽃"},
								   { "어머니는 주인아저씨가 안 보고 있다 싶어지자/ 내 투가리에 국물을 부어 주셨습니다 .../ 주인아저씨는 우리가 미안한 마음 안 느끼게 조심,/ 성냥갑만 한 깍두기 한 접시를 놓고/ 돌아서는 거였습니다 ...", "나는 얼른 이마에 흐른 땀을 훔쳐 내려/ 눈물을 땀인 양 만들어놓고 나서,/ 물수건으로 눈동자에서 난 땀을 씻어 냈습니다/ 그러면서 속으로 중얼거렸습니다 ...", "함민복", "눈물은 왜 짠가"},
								   { "희망찬 사람은/ 그 자신이 희망이다/ / 길 찾는 사람은/ 그 자신이 새 길이다 ...", "참 좋은 사람은/ 그 자신이 이미 좋은 세상이다/ / 사람 속에 들어 있다/ 사람에서 시작된다 ...", "박노해", "다시"},
								   { "내 그대를 생각함은/ 항상 그대가 앉아 있는 배경에서/ 해가 지고 바람이 부는 일처럼 사소한 일일 것이나", "언젠가 그대가/ 한없이 괴로움 속을 헤메일 때에/ 오랫동안 전해오던 그 사소함으로/ 그대를 불러보리라. ...", "황동규", "즐거운 편지"},
								   { "네가 오기로 한 그 자리에/ 내가 미리 가 너를 기다리는 동안/ 다가오는 모든 발자국은/ 내 가슴에 쿵쿵거린다 ...", "오지 않는 너를 기다리며/ 마침내 나는 너에게 간다/ 아주 먼 데서 나는 너에게 가고/ 아주 오랜 세월을 다하여 너는 지금 오고 있다 ...", "황지우", "너를 기다리는 동안"},
								   { "흔들리는 나뭇가지에 꽃 한번 피우려고/ 눈은 얼마나 많은 도전을 멈추지 않았으랴 ...", "봄이면 가지는 그 한번 덴 자리에/ 세상에서 가장 아름다운 상처를 터뜨린다", "고재종", "첫사랑"},
								   { "꽃이/ 피는 건 힘들어도/ 지는 건 잠깐이더군 ...", "꽃이/ 지는 건 쉬워도/ 잊는 건 한참이더군/ 영영 한참이더군", "최영미", "선운사에서"},
								   { "이제 단풍든 이 골짜기에서/ 서둘러 노스탤지어를 말하지 말라 ...", "한 시절의 그늘을 온몸으로 섬긴 후에야/ 겨울산으로 돌아가는 자작나무/ 자작나무에 기대어서만 자작나무를 말할 일이다", "김선우", "시간은 오래 지속된다"},
								   { "늦가을 평상에 앉아/바다로 가는 길의 끝에다/지그시 힘을 준다 시린 바람이/옛날 노래가 적힌 악보를 넘기고 있다 ...", "염전이 있던 곳/나는 마흔 살/옛날은 가는 게 아니고/이렇게 자꾸 오는 것이었다", "이문재", "소금창고"},
								   { "추우면 몸을 최대한 웅크릴 것/남이 닦아논 길로만 다니되/수상한 곳엔 그림자도 비추지 말며/자신을 너무 오래 들여다보지 말 것 ...", "잘 보낸 하루가 그저 그렇게 보낸 십년 세월을/보상할 수도 있다고, 정말로 그렇게 믿을 것/그러나 태양 아래 새로운 것은 없고/인생은 짧고 하루는 길더라", "최영미", "행복론"},
								   { "꼭 한 번씩 찾아오는/어둠 속에서도 진흙 속에서도/제대로 살자 ... ", "창호지 흔드는 바람 앞에서/은사시 때리는 눈보라 앞에서/오늘 하루를 사무치게 살자 ...", "도종환", "오늘 하루"},
								   { "처음 열린 물길은 짧고 어색해서/서로 물을 보내고 자주 섞여야겠지만/한 세상 유장한 정성의 물길이 흔할 수야 없겠지.", "긴 말 전하지 않아도 미리 물살로 알아듣고/몇 해쯤 만나지 못해도 밤잠이 어렵지 않은 강,/아무려면 큰 강이 아무 의미도 없이 흐르고 있으랴. ...", "마종기", "우화의 강"},
								   { "아주 오랜 세월이 흐른 뒤에/힘없는 책갈피는 이 종이를 떨어뜨리리/그때 내 마음은 너무나 많은 공장을 세웠으니/어리석게도 그토록 기록할 것이 많았구나 ...", "그리하여 나는 우선 여기에 짧은 글을 남겨둔다/나의 생은 미친 듯이 사랑을 찾아 헤매었으나/단 한번도 스스로를 사랑하지 않았노라", "기형도", "질투는 나의 힘"},
								   { "시에는 무슨 근사한 얘기가 있다고 믿는/낡은 사람들이/아직도 살고 있다. 시에는/아무것도 없다 ...", "시에는 아무것도 없다. 시에는/남아있는 우리의 생 밖에./남아있는 우리의 생은 우리와 늘 만난다/조금도 근사하지 않게. ...", "오규원", "용산에서"},
								   { "어느/늦은 저녁 나는/흰 공기에 담긴 밥에서/김이 피어 올라오는 것을 보고 있었다", "그때 알았다/무엇인가 영원히 지나가버렸다고/지금도 영원히/지나가버리고 있다고/ / 밥을 먹어야지 ...", "한강", "어느 늦은 저녁 나는"},
								   { "정말 그럴 때가 있을 겁니다./어디 가나 벽이고 무인도이고/혼자라는 생각이 들 때가 있을 겁니다. ...", "그런 때에는 연필 한 자루 잘 깎아/글을 씁니다/ /사소한 것들에 대하여 ...", "이어령", "정말 그럴 때가"},
								   { "길을 잃어보지 않은 사람은 모르리라/터덜거리며 걸어간 길 끝에/멀리서 밝혀져오는 불빛의 따뜻함을 ... ", "먼 곳의 불빛은/나그네를 쉬게 하는 것이 아니라/계속 걸어갈 수 있게 해준다는 것을", "나희덕", "산속에서"},
								   { "처음엔 당신의 착한 구두를 사랑했습니다/그러다 그 안에 숨겨진 발도 사랑하게 되었습니다... / /당신은 저의 어디부터 시작했나요 ... /대답하지 않으셔도 됩니다 제가 그러했듯이 ...", "처음엔 당신의 구두를 사랑했습니다 .../이제는 당신의 구두가 가는 곳과/손길이 닿는 곳을 사랑하기 시작합니다/언제나 시작입니다", "성미정", "처음엔 당신의 착한 구두를 사랑했습니다"},
								   { "울지 말게/다들 그렇게 살아가고 있어/날마다 어둠 아래 누워 뒤척이다, 아침이 오면/개똥 같은 희망 하나 가슴에 품고/다시 문을 나서지 ...", "자, 한잔 들게나/되는 게 없다고, 이놈의 세상/되는 게 좆도 없다고/술에 코 박고 우는 친구야", "백창우", "소주 한잔 했다고 하는 얘기가 아닐세"},
								   { "공양미 삼백 석을 구하지 못하여/당신이 평생을 어둡더라도/결코 인당수에 빠지지는 않겠습니다/어머니,/저는 여기 남아 책을 보겠습니다 ...", "그 대신 점자책을 사드리겠습니다/어머니,/점자 읽는 법도 가르쳐드리지요 .../ /어디에도 인당수는 없습니다 ...", "김승희", "배꼽을 위한 연가 5"},
								   { "사랑하는 사람의 심장무게가 얼마나 되는지 알아요?/두근 두근 합해서 네근이랍니다/여러분을 만나러 오는/내 마음이 그랬습니다 ...", "누군가의 그 말이/내 심장을 쳤습니다/언젠가 여러분을 만날 때/나도 그 말 꼭 빌려 써야겠습니다 ...", "천양희", "누군가의 그 말"},
								   { "술에 취하여/나는 수첩에다가 뭐라고 써 놓았다./술이 깨니까/나는 그 글씨를 알아볼 수가 없었다.", "세 병쯤 소주를 마시니까/다시는 술 마시지 말자/고 써 있는 그 글씨가 보였다", "김영승", "반성 16"},
								   { "돌부처는/눈 한 번 감았다 뜨면 모래무덤이 된다/눈 깜짝할 사이도 없다", "그대여/모든 게 순간이었다고 말하지 말라/달은 윙크 한 번 하는 데 한 달이나 걸린다", "이정록", "더딘 사랑"},
								   { "즐거운 날 밤에는/한 개도 없더니/한 개도 없더니/ /마음 슬픈 밤에는/하늘 가득 별이다. ...", "울고 싶은 밤에는/가슴에도/별이다./ /온 세상이/별이다.", "공재동", "별"},
								   { "아침저녁 한 움큼씩/약을 먹는다 약 먹는 걸/더러 잊는다고 했더니/의사선생은 벌컥 화를 내면서/그게 목숨 걸린 일이란다 ...", "쭈글거리는 내 몰골이 안돼 보였던지/제자 하나가 날더러 제발/나이 좀 먹지 말라는데/그거 안먹으면 깜박 죽는다는 걸/녀석도 깜박 잊었나보다", "정양", "그거 안 먹으면"},
								   { "첫 출근하는 날,/신발 끈을 매면서 먹은 마음으로/직장일은 한다면./ /나는 너, 너는 나라며 화해하던/그날의 일치가 가시지 않는다면.", "이 사람은, 그때가 언제이든지/늘 새마음이기 때문에/바다로 향하는 냇물처럼 날마다가 새로우며,/깊어지며, 넓어진다", "정채봉", "첫마음"}
								};

	public Poem() {
		randomN = rand.nextInt(poemList.length);

	}

	public String getPoemL() {
		StringTokenizer st = new StringTokenizer(poemList[randomN][0], "/");
		String result = "<html><body><i>'"+st.nextToken();
		while (st.hasMoreTokens()) {
			result += "<br>" + st.nextToken();
		}
		result += "'</i></body></html>";

		return result;
	}

	public String getPoemR() {
		StringTokenizer st = new StringTokenizer(poemList[randomN][1], "/");
		String result = "<html><body><i>'"+st.nextToken();
		while (st.hasMoreTokens()) {
			result += "<br>" + st.nextToken();
		}
		result += "'</i></body></html>";
		return result;
	}

	public String getAuth() {
		return "- " + poemList[randomN][2]+ "  <"+poemList[randomN][3]+">";
	}


}
