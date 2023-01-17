package app.lahzebar

import app.lahzebar.domain.model.room.Comment
import app.lahzebar.domain.model.room.Post
import app.lahzebar.domain.model.room.User
import java.util.Random

object SampleData {
    private val imageUrls = mutableListOf(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_90ElEEv49oaiB3tDPR1VsleXcrbtzhV7ZQ&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3dwt7rLZtgejIRf3eVNfRIPlix9qsVbfgmA&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVNEQ9Ng1K9fM8fJ9Z2wOYBSJYgtNIqodm6Q&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdInuDRrW4YVJrtI47s5QCxpPVISIX9kVe-w&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmjV151HuqcnRAFsSsJCIU6K-BB_uRIPZc9A&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR05cpr9wvrzmqb5xdXcvlcSJRT0gDEbUPIQg&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeZ4vZUAH6xMXTuFzT6M5B6dUqy-MIFDoldQ&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdCsMBOAM9hWmfSpH3atS75RcKdnIozOahVQ&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1pm-IVP_gxg_ScsvAis4YBo9u1kPG6SQqxw&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRCFRLEkeqJIgMjR_HYiV1WiHCwej9kAUUJlg&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAsaQG48XCf8o_YY8NsGiuvAiShfG6SeaHZQ&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQ9lFtzSWxuWHTZ0oVgPetYAEP_-ulOyHbcA&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9maQVXwk4F5dQ28mOUD_Cen8Q6SNgnjd9SatvdKEgNCWAssH1qdJra7L6RGcNW7B_34c&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtVUZUMt3xseK1JFMMi7ayl7iazOXO7TMUoaadkTWTaSUvtm7Ex3MZG3NJezEC7sFMy0g&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQW5GUfwXIFERtGLZOepxwWk3O_AjOxxvb_DQ&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgl27zfvPh0pCDZAEboNQAqVopeTEn1mVuCg&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmjV151HuqcnRAFsSsJCIU6K-BB_uRIPZc9A&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKDeiUyV58AOi-1EiUFKqvd5i8gtcMTTv-7g&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdInuDRrW4YVJrtI47s5QCxpPVISIX9kVe-w&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXF0Xo6AIgIGqcraRimblfJxYFQvnfaNWO-g&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1ARroMsIJEaJ22QKdGBOD9K9MMk10O3c2Qg&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_aiwg5UFwYoa4IUgCU334aiLSkg41DHCdqw&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjG3HT1RftQFcT7405PEh79zqpPZu5viJbcA&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxDsOvzZ_yCAwi7RVI_d14MEJ7IvkPOFKGjA&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-q5lQkv8q002BxE62aJrYKTI3-AZ7xxC1caSvfPstoQbgn9A6gm3NX2JYa5rFY8_1X_A&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvAFVWctZj2qy-7G1TT-PQQUgz5Zdng1p7dw&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5YMaLO0_YG8rnW73T9uhpms4QgFfD7jZ0Sg&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQndDjJLFt-kdnHfabfwNJICg-Cn9gfz7CVgA&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY4bCD_lJoIAIsSRCp1cbJd6QHF1rrx1Gefg&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYGjY21AD4Sc7rDcaxyLVtfZl4Gh132GLmuA&usqp=CAU"

    )

    private val captions = mutableListOf(
        "Love is composed of a single soul inhabiting two bodies.– Aristotle",
        "“All you need is love. But a little chocolate now and then doesn’t hurt.” – Charles M. Schulz",
        "“Go confidently in the direction of your dreams.” – Henry David Thoreau",
        "“I can accept failure. Everyone fails at something. But I can’t accept not trying.” —Michael Jordan",
        "“If you don’t like something, change it. If you can’t change it, change your attitude.” –Maya Angelou",
        "“If you want the rainbow, you gotta put up with the rain.” – Dolly Parton",
        "“It’s kind of fun to do the impossible.” – Walt Disney",
        "“Never let the fear of striking out keep you from playing the game.” – Babe Ruth",
        "“Sometimes you will never know the value of a moment until it becomes a memory.” – Dr. Seuss",
        "“The only place where success comes before work is in the dictionary.” – Vidal Sassoon",
        "Whatever is good for your soul, do that",
        "Even the stars were jealous of the sparkle in her eyes",
        "Stress less and enjoy the best",
        "Get out there and live a little",
        "I’m not high maintenance, you’re just low effort",
        "I’m not gonna sugar coat the truth, I’m not Willy Wonka",
        "Life is better when you’re laughing",
        "Look for the magic in every moment",
        "Vodka may not be the answer but it’s worth a shot",
        "A sass a day keeps the basics away",
        "It may look like I’m not doing anything, but in my mind I’m pretty busy",
        "In my defence, I was left unsupervised",
        "It may look like I’m not doing anything, but in my mind I’m pretty busy",
        "In like a lion, out like a lamb",
        "Spring is in the air",
        "Gonna be a bright, bright sunshiny day",
        "Sunlight is the best disinfectant",
        "The weather outside is frightful",
        "First I drink the coffee, then I do the things",
        "There’s always a wild side to an innocent face"

    )

    private val names = mutableListOf(
        "Adiran",
        "Adora",
        "Albinka",
        "Perenna",
        "Anneliese",
        "Christabella",
        "Christina",
        "Columbina",
        "Dulcinea",
        "Eileen",
        "Ephyra",
        "Fabia",
        "Fabianna",
        "Fan",
        "Fascienne",
        "Faviola",
        "Garnet",
        "Gemma",
        "Georgina",
        "Gladys",
        "Hajnal",
        "Harpinna",
        "Helen",
        "Hippolyte",
        "Ignacia",
        "Immaculata",
        "Ince",
        "Iphinome",
        "Verda",
        "Xanthe"
    )

    private val comments = mutableListOf(
        "You look amazing",
        "You’re killing it",
        "So pretty!",
        "Those lips tho",
        "This outfit is absoultely insane",
        "So beautiful ",
        "You are stunning",
        "You’re the cutest thing",
        "Oh my lawd",
        "This outfit and you in it",
        "You’re the man",
        "Such a beauty",
        "Hell yeah",
        "Dream physique",
        "You’re a total cutie",
        "Rock on bro",
        "Now that’s a lovely pic",
        "Hard look",
        "Absolute beauty right here",
        "o gorgeous you",
        "To look like this",
        "You’re one handsome young fella",
        "You’re literally unreal",
        "I’m so obssessed",
        "Always popping off",
        "So sick",
        "Just like that ",
        "This is mood ",
        "The vibes are immaculate",
        "Yea, you’re unreal",
    )

    private val random = Random()

    private fun generateLikeNumber() = random.nextInt(1000 - 50) + 100

    fun generatePrimaryData(): Triple<List<User>, List<Post>, List<Comment>> {
        val users = mutableListOf<User>()
        val post = mutableListOf<Post>()
        val comment = mutableListOf<Comment>()
        for (i in 0 until 30) {
            val id = i.plus(1)
            users.add(User(userId = id, name = names[i]))

            for (j in 0 until 30) {
                val commentId = j.plus(1)

                comment.add(Comment(commentId = commentId, postId = id, title = comments[j]))
            }
            post.add(
                Post(
                    postId = id, userId = id, caption = captions[i], image = imageUrls[i],
                    like = generateLikeNumber()
                )
            )
        }

        return Triple(first = users, second = post, third = comment)
    }
}
