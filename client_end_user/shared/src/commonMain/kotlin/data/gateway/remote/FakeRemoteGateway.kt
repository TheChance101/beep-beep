package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.OfferDto
import domain.entity.Offer
import domain.gateway.IFakeRemoteGateway


class FakeRemoteGateway : IFakeRemoteGateway {

    override fun getNewOffers() : List<Offer>{
        return offers.map { it.toEntity() }
    }


    private val offers = listOf(
        OfferDto(
            "000-0d-d0-d0--00d0d0d",
            "https://s3-alpha-sig.figma.com/img/74ff/c283/fb93589c0fb95bffae890164ec2aba74?Expires=1694390400&Signature=hKha19ACxyhhz-Wmwn~npU9SX52UpPYLqkqBT6hx1YZ6f~RG6XrFJhEcpsFMaw5kXGYS5g7hrysHkk5PbiCLzSk3OJEQNBQeNJlCyWKT-f5ckGEbkDYfvO5WmipDAMMHSTxilNY3-xK9h3vQyOCbZZdY75b8k7cuPLrAMmKBRRDM5NJeSGSJGrrNBQNhsK8MjlxUE5aHg2Sqy8UKseNPZsks4lWNWVXmI3QDgEooOXC5CzZ6ivorXagM7s-1-WOZYdTkr0F9mPZZ8rfFZzMeF~OvokA6gd4-6IXQaK4gWoQwVTSgGxMspmcqQU59EOTESRoVwd4CLaf-lFXU9vCaaQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        OfferDto(
            "000-0d-d0-d0--00d0d0dacsccs",
            "https://s3-alpha-sig.figma.com/img/44f7/7fd1/8c37e4958c7caca679d133c2374c85a6?Expires=1694390400&Signature=j9wjjnLOGs1wG-C8T1riMy6npf-G0V0VARSQGtIgqM3L0lCMY-yJbBcWEEsoGuUdPXZzGTlon-RWMDnRChj2k51akbCp4rLuv-UYdtMIDXljS5hcGdVi0bYkI7V4nY4Jp2vkQdKZuY73N5Yy49WfyElVfsB8n1Qgi4hMxtsicAzF0-5bZnyaLQZda2LPK-rWVH2yj5rhR2evzrcrKYRVY2GaYNQQ58zsHdwvieq4IKlHx44RPg~x2kG~ObSAmzW1iQ8LEEF8~jhI9S~Iw3TSbejP0T64qYpA21erIX8DPLfygUOB0XvhT41vsR-nkVsiYRHXcIyAet-Apj5V5xCevA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        OfferDto(
            "000-0casscac--c-s-cs",
            "https://s3-alpha-sig.figma.com/img/74ff/c283/fb93589c0fb95bffae890164ec2aba74?Expires=1694390400&Signature=hKha19ACxyhhz-Wmwn~npU9SX52UpPYLqkqBT6hx1YZ6f~RG6XrFJhEcpsFMaw5kXGYS5g7hrysHkk5PbiCLzSk3OJEQNBQeNJlCyWKT-f5ckGEbkDYfvO5WmipDAMMHSTxilNY3-xK9h3vQyOCbZZdY75b8k7cuPLrAMmKBRRDM5NJeSGSJGrrNBQNhsK8MjlxUE5aHg2Sqy8UKseNPZsks4lWNWVXmI3QDgEooOXC5CzZ6ivorXagM7s-1-WOZYdTkr0F9mPZZ8rfFZzMeF~OvokA6gd4-6IXQaK4gWoQwVTSgGxMspmcqQU59EOTESRoVwd4CLaf-lFXU9vCaaQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        OfferDto(
            "000-0wdwdwdww-d-w-d-wd-dw",
            "https://s3-alpha-sig.figma.com/img/44f7/7fd1/8c37e4958c7caca679d133c2374c85a6?Expires=1694390400&Signature=j9wjjnLOGs1wG-C8T1riMy6npf-G0V0VARSQGtIgqM3L0lCMY-yJbBcWEEsoGuUdPXZzGTlon-RWMDnRChj2k51akbCp4rLuv-UYdtMIDXljS5hcGdVi0bYkI7V4nY4Jp2vkQdKZuY73N5Yy49WfyElVfsB8n1Qgi4hMxtsicAzF0-5bZnyaLQZda2LPK-rWVH2yj5rhR2evzrcrKYRVY2GaYNQQ58zsHdwvieq4IKlHx44RPg~x2kG~ObSAmzW1iQ8LEEF8~jhI9S~Iw3TSbejP0T64qYpA21erIX8DPLfygUOB0XvhT41vsR-nkVsiYRHXcIyAet-Apj5V5xCevA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        )
    )

}