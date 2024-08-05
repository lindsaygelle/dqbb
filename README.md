# dqbb

Dragon Quest Battle Builder (DQBB) is a turn-based battle simulator inspired by the classic Dragon Quest game. It allows users to create custom teams with configurable characters, enabling them to strategize and pit their teams against one another in simulated battles.

# Overview
The battle simulator allows for the creation of characters with detailed configurations, including attributes, abilities, and decision-making logic. These characters can be assembled into teams to battle each other, with each battle playing out based on the characters' configurations and the strategies they employ.

# Character Configuration
Characters in the simulator are defined using a JSON configuration. Each character has various attributes such as agility, damage resistance, hit points, and more. Below is an example configuration for a character named WRAITH KNIGHT:

```json
{
    "agility": 56,
    "damageResistance": 52,
    "decisions": [ ... ],
    "experiencePoints": 28,
    "goldPoints": 120,
    "hitPointsMaximum": 46,
    "id": "17",
    "magicPointsMaximum": 20,
    "name": "WRAITH_KNIGHT",
    "pattern": "176",
    "statusResistance": 80,
    "strength": 68
}
```

# Abilities and Decisions
Characters can perform various abilities during battle, such as healing or attacking. These abilities are defined in the decisions array within the character's configuration. Each decision specifies an ability, preconditions for that ability, and target selection criteria.

## Example Decision
The WRAITH KNIGHT has two decisions defined: one for healing and one for attacking.

### Healing

```json 
{
    "ability": {
        "actionType": "HEAL",
        "conditionType": "HIT_POINTS",
        "orderType": "MIN"
    },
    "preCondition": {
        "matchType": "ALL",
        "qualifiers": [ ... ]
    },
    "priorityType": "HIGHEST",
    "targetSelection": {
        "matchType": "ANY",
        "qualifiers": [ ... ]
    }
}

```

### Attacking
```json
{
    "ability": {
        "actionType": "ATTACK",
        "conditionType": "HIT_POINTS",
        "orderType": "MAX"
    },
    "preCondition": {
        "matchType": "ANY",
        "qualifiers": [ ... ]
    },
    "priorityType": "LOWEST",
    "targetSelection": {
        "matchType": "ANY",
        "qualifiers": [ ... ]
    }
}

```

# Conditions and Targeting
Conditions and targeting rules dictate when and how abilities are used. These rules can be based on various attributes of the characters involved in the battle.

```json
{
    "agility": 56,
    "damageResistance": 52,
    "decisions": [
        {
            "ability": {
                "actionType": "HEAL",
                "conditionType": "HIT_POINTS",
                "orderType": "MIN"
            },
            "preCondition": {
                "matchType": "ALL",
                "qualifiers": [
                    {
                        "actorCheckers": [
                            {
                                "conditionType": "HIT_POINTS",
                                "expressionType": "PERCENTAGE",
                                "operatorType": "LESS_THAN",
                                "priorityType": "HIGHEST",
                                "value": 26
                            }
                        ],
                        "matchType": "ANY",
                        "priorityType": "HIGHEST",
                        "targetType": "ALLY"
                    },
                    {
                        "actorCheckers": [
                            {
                                "conditionType": "MAGIC_POINTS",
                                "expressionType": "EXACT",
                                "operatorType": "GREATER_THAN",
                                "priorityType": "HIGHEST",
                                "value": 1
                            }
                        ],
                        "matchType": "ANY",
                        "priorityType": "HIGHEST",
                        "targetType": "SELF"
                    }
                ]
            },
            "priorityType": "HIGHEST",
            "targetSelection": {
                "matchType": "ANY",
                "qualifiers": [
                    {
                        "actorCheckers": [
                            {
                                "conditionType": "HIT_POINTS",
                                "expressionType": "PERCENTAGE",
                                "operatorType": "LESS_THAN",
                                "priorityType": "HIGHEST",
                                "value": 26
                            }
                        ],
                        "matchType": "ANY",
                        "priorityType": "HIGHEST",
                        "targetType": "ALLY"
                    }
                ]
            }
        },
        {
            "ability": {
                "actionType": "ATTACK",
                "conditionType": "HIT_POINTS",
                "orderType": "MAX"
            },
            "preCondition": {
                "matchType": "ANY",
                "qualifiers": [
                    {
                        "actorCheckers": [
                            {
                                "conditionType": "HIT_POINTS",
                                "expressionType": "EXACT",
                                "operatorType": "GREATER_THAN",
                                "priorityType": "HIGHEST",
                                "value": 0
                            }
                        ],
                        "matchType": "ANY",
                        "priorityType": "HIGHEST",
                        "targetType": "ENEMY"
                    }
                ]
            },
            "priorityType": "LOWEST",
            "targetSelection": {
                "matchType": "ANY",
                "qualifiers": [
                    {
                        "actorCheckers": [
                            {
                                "conditionType": "HIT_POINTS",
                                "expressionType": "EXACT",
                                "operatorType": "GREATER_THAN",
                                "priorityType": "HIGHEST",
                                "value": 0
                            }
                        ],
                        "matchType": "ANY",
                        "priorityType": "HIGHEST",
                        "targetType": "ENEMY"
                    }
                ]
            }
        }
    ],
    "experiencePoints": 28,
    "goldPoints": 120,
    "hitPointsMaximum": 46,
    "id": "17",
    "magicPointsMaximum": 20,
    "name": "WRAITH_KNIGHT",
    "pattern": "176",
    "statusResistance": 80,
    "strength": 68
}
```
